import torch
import torchvision.models as model
import timm
import torch.nn as nn
import matplotlib.pyplot as plt
from torch.utils.data import Dataset, DataLoader, Subset
from torchvision import transforms,datasets
import os
import glob
from tqdm import tqdm
from PIL import Image
import random
from sklearn.model_selection import train_test_split
import torch
import cv2

import numpy as np
import torch.nn.functional as F
import matplotlib.pyplot as plt
from sklearn.metrics import mean_squared_error
os.environ['CUDA_VISIBLE_DEVICES'] = '0'
import random
from tqdm import tqdm

Model = timm.create_model('resnet18', pretrained=True, num_classes=8)


class load_data(Dataset):
    def __init__(self, path):
        self.img_list = glob.glob(path)

    def __len__(self):
        return len(self.img_list)

    def __getitem__(self, index):
        img_name = self.img_list[index]
        ret_img = cv2.imread(img_name)
        ret_coord = img_name.split('/')[-1]
        ret_coord = ret_coord.split(".jpg")[0]
        ret_coord = ret_coord.replace("__", "_")
        # Check if the ret_coord has the format "train\0.3822"
        if "\\" in ret_coord:
            ret_coord = ret_coord.split("\\")[1]  # Extract the value after the backslash

        ret_coord = list(map(float, ret_coord.split("_")))
        tp = random.randint(1, 5)  # related to shearing
        tp2 = random.randint(1, 4)  # related to brightness
        # shear transformation
        if tp == 1:  # shearing with y1
            ret_img, ret_coord = self.shear_y1(ret_img, ret_coord)
            # elf.visualization(ret_img, ret_coord)
        elif tp == 2:  # shearing with y2
            ret_img, ret_coord = self.shear_y2(ret_img, ret_coord)
            # elf.visualization(ret_img, ret_coord)
        elif tp == 3:  # shearing with x1
            ret_img, ret_coord = self.shear_x1(ret_img, ret_coord)
            # self.visualization(ret_img, ret_coord)
        elif tp == 4:  # shearing with x2
            ret_img, ret_coord = self.shear_x2(ret_img, ret_coord)
            # elf.visualization(ret_img, ret_coord)
        else:  # no shaering
            ret_img, ret_coord = self.original(ret_img, ret_coord)
            # elf.visualization(ret_img, ret_coord)
        ret_img = cv2.resize(ret_img, dsize=(128, 128))
        #         plt.imshow(ret_img)
        #         plt.show()
        # brightness augmentation
        # tp2 = 4
        if tp2 == 1:  # no augmentation
            pass
        elif tp2 == 2:  # whole brighten
            rnd_b = int(random.uniform(0, 50))
            array = np.full(ret_img.shape, (rnd_b, rnd_b, rnd_b), dtype=np.uint8)
            ret_img = cv2.add(ret_img, array)
        elif tp2 == 3:  # whole darken
            rnd_b = int(random.uniform(0, 60))
            array = np.full(ret_img.shape, (rnd_b, rnd_b, rnd_b), dtype=np.uint8)
            ret_img = cv2.subtract(ret_img, array)

        else:  # horizontal shadow (always the upper the darker because of shadow)
            rnd_cover = int(random.uniform(54, 74))
            rnd_step = int(random.uniform(13, 50))
            rnd_tilt = int(random.randint(-1, 1))

            mask_cover = rnd_cover
            mask = np.zeros(ret_img.shape[0:2], dtype=np.uint8)  # (128, 256)

            for col in range(mask.shape[1]):
                for row in range(mask.shape[0]):
                    if row <= mask_cover:
                        mask[row][col] = 255
                if (col + 1) % rnd_step == 0:
                    mask_cover = mask_cover + rnd_tilt
            rnd_b = int(random.uniform(0, 50))
            array = np.full(ret_img.shape, (rnd_b, rnd_b, rnd_b), dtype=np.uint8)
            shadowed = cv2.subtract(ret_img, array, mask=mask)
            for row in range(shadowed.shape[0]):
                for col in range(shadowed.shape[1]):
                    if shadowed[row][col][0] == 0:
                        shadowed[row][col] = ret_img[row][col]
            ret_img = shadowed
        ret_img = ret_img / 255.0
        ret_coord = np.array(ret_coord)
        ret_img = ret_img.transpose((2, 0, 1))

        return ret_img, ret_coord, tp

    ########################################################################################################
    def visualization(self, input_img, coord, color=(0, 0, 255)):
        h, w = input_img.shape[:2]
        coord_list = [(int(w * coord[0]), int(h * coord[1])),
                      (int(w * coord[2]), int(h * coord[3])),
                      (int(w * coord[4]), int(h * coord[5])),
                      (int(w * coord[6]), int(h * coord[7]))]
        return coord_list

    ########################################################################################################
    def shear_y1(self, input_img, ret_coord):
        src = input_img
        h, w = src.shape[:2]
        # shear 주는 정도
        degree = random.uniform(0, 0.5)
        degree = round(degree, 2)
        # image shearing
        affy = np.array([[1, 0, 0], [degree, 1, 0]], dtype=np.float32)
        dsty = cv2.warpAffine(src, affy, (w, h + int(w * degree)))
        # 좌표 shearing
        hw = np.array([[w, 0], [0, h]])
        ret_coord = np.reshape(ret_coord, (4, 2))
        r = np.dot(ret_coord, hw)
        nh, nw = dsty.shape[:2]
        r = [[r[0][0], r[0][1] + r[0][0] * degree], [r[1][0], r[1][1] + r[1][0] * degree],
             [r[2][0], r[2][1] + r[2][0] * degree], [r[3][0], r[3][1] + r[3][0] * degree]]
        r = [[r[0][0] / nw, r[0][1] / nh], [r[1][0] / nw, r[1][1] / nh], [r[2][0] / nw, r[2][1] / nh],
             [r[3][0] / nw, r[3][1] / nh]]
        ret_coord2 = np.array([r[0][0], r[0][1], r[1][0], r[1][1], r[2][0], r[2][1], r[3][0], r[3][1]])
        coord = self.visualization(dsty, ret_coord2)
        coord_x = []
        coord_y = []
        for i in coord:
            coord_x.append(i[0])
            coord_y.append(i[1])
        min_x = min(coord_x);
        min_y = min(coord_y)
        max_x = max(coord_x);
        max_y = max(coord_y)
        # cropped image의 padding
        pad_xr = nw * (random.uniform(0.01, 0.03))
        pad_xl = nw * (random.uniform(0.01, 0.03))
        pad_yu = nh * (random.uniform(0.005, 0.015))
        pad_yd = nh * (random.uniform(0.005, 0.015))

        cropped_img = dsty[int(min_y - pad_yu): int(min_y - pad_yu + max_y - min_y + pad_yu + pad_yd),
                      int(min_x - pad_xl): int(min_x - pad_xl + max_x - min_x + pad_xr + pad_xl)]

        coord_x = list(map(lambda x: x - int(min_x - pad_xl), coord_x))
        coord_y = list(map(lambda x: x - int(min_y - pad_yu), coord_y))
        new_coord = []
        h2, w2 = cropped_img.shape[:2]
        for i in range(4):
            new_coord.append(coord_x[i] / w2)
            new_coord.append(coord_y[i] / h2)
        return cropped_img, new_coord

    ########################################################################################################
    def shear_y2(self, input_img, ret_coord):
        src = input_img
        h, w = src.shape[:2]
        # shear 주는 정도
        degree = random.uniform(-0.5, 0)
        degree = round(degree, 2)
        # image shearing
        affy = np.array([[1, 0, 0], [degree, 1, -degree * w]], dtype=np.float32)
        degree = -degree
        dsty = cv2.warpAffine(src, affy, (w, h + int(w * degree)))
        # 좌표 shearing
        hw = np.array([[w, 0], [0, h]])
        ret_coord = np.reshape(ret_coord, (4, 2))
        nh, nw = dsty.shape[:2]
        degree = -degree
        r = np.dot(ret_coord, hw)
        r = [[r[0][0], r[0][1] + r[0][0] * degree - degree * w], [r[1][0], r[1][1] + r[1][0] * degree - degree * w],
             [r[2][0], r[2][1] + r[2][0] * degree - degree * w], [r[3][0], r[3][1] + r[3][0] * degree - degree * w]]
        r = [[r[0][0] / nw, r[0][1] / nh], [r[1][0] / nw, r[1][1] / nh], [r[2][0] / nw, r[2][1] / nh],
             [r[3][0] / nw, r[3][1] / nh]]
        ret_coord2 = np.array([r[0][0], r[0][1], r[1][0], r[1][1], r[2][0], r[2][1], r[3][0], r[3][1]])
        # print(ret_coord2)
        coord = self.visualization(dsty, ret_coord2)
        coord_x = []
        coord_y = []
        for i in coord:
            coord_x.append(i[0])
            coord_y.append(i[1])
        min_x = min(coord_x);
        min_y = min(coord_y)
        max_x = max(coord_x);
        max_y = max(coord_y)
        # cropped image의 padding
        pad_xr = nw * (random.uniform(0.01, 0.03))
        pad_xl = nw * (random.uniform(0.01, 0.03))
        pad_yu = nh * (random.uniform(0.005, 0.015))
        pad_yd = nh * (random.uniform(0.005, 0.015))
        cropped_img = dsty[int(min_y - pad_yu): int(min_y - pad_yu + max_y - min_y + pad_yu + pad_yd),
                      int(min_x - pad_xl): int(min_x - pad_xl + max_x - min_x + pad_xr + pad_xl)]
        coord_x = list(map(lambda x: x - int(min_x - pad_xl), coord_x))
        coord_y = list(map(lambda x: x - int(min_y - pad_yu), coord_y))
        new_coord = []
        h2, w2 = cropped_img.shape[:2]
        for i in range(4):
            new_coord.append(coord_x[i] / w2)
            new_coord.append(coord_y[i] / h2)
        return cropped_img, new_coord

    ########################################################################################################
    def shear_x1(self, input_img, ret_coord):
        src = input_img
        h, w = src.shape[:2]
        # shear 주는 정도
        degree = random.uniform(0, 0.5)
        degree = round(degree, 2)
        # image shearing
        affx = np.array([[1, degree, 0], [0, 1, 0]], dtype=np.float32)
        dstx = cv2.warpAffine(src, affx, (w + int(h * degree), h))
        # 좌표 shearing
        hw = np.array([[w, 0], [0, h]])
        ret_coord = np.reshape(ret_coord, (4, 2))
        nh, nw = dstx.shape[:2]
        r = np.dot(ret_coord, hw)
        r = [[r[0][0] + r[0][1] * degree, r[0][1]], [r[1][0] + r[1][1] * degree, r[1][1]],
             [r[2][0] + r[2][1] * degree, r[2][1]], [r[3][0] + r[3][1] * degree, r[3][1]]]
        r = [[r[0][0] / nw, r[0][1] / nh], [r[1][0] / nw, r[1][1] / nh], [r[2][0] / nw, r[2][1] / nh],
             [r[3][0] / nw, r[3][1] / nh]]
        ret_coord2 = np.array([r[0][0], r[0][1], r[1][0], r[1][1], r[2][0], r[2][1], r[3][0], r[3][1]])
        coord = self.visualization(dstx, ret_coord2)
        coord_x = []
        coord_y = []
        for i in coord:
            coord_x.append(i[0])
            coord_y.append(i[1])
        min_x = min(coord_x);
        min_y = min(coord_y)
        max_x = max(coord_x);
        max_y = max(coord_y)
        # cropped image의 padding
        pad_xr = nw * (random.uniform(0.01, 0.03))
        pad_xl = nw * (random.uniform(0.01, 0.03))
        pad_yu = nh * (random.uniform(0.005, 0.015))
        pad_yd = nh * (random.uniform(0.005, 0.015))
        cropped_img = dstx[int(min_y - pad_yu): int(min_y - pad_yu + max_y - min_y + pad_yu + pad_yd),
                      int(min_x - pad_xl): int(min_x - pad_xl + max_x - min_x + pad_xr + pad_xl)]
        coord_x = list(map(lambda x: x - int(min_x - pad_xl), coord_x))
        coord_y = list(map(lambda x: x - int(min_y - pad_yu), coord_y))
        new_coord = []
        h2, w2 = cropped_img.shape[:2]
        for i in range(4):
            new_coord.append(coord_x[i] / w2)
            new_coord.append(coord_y[i] / h2)
        return cropped_img, new_coord

    ########################################################################################################
    def shear_x2(self, input_img, ret_coord):
        src = input_img
        h, w = src.shape[:2]
        # shear 주는 정도
        degree = random.uniform(-0.5, 0)
        degree = round(degree, 2)
        # image shearing
        affx = np.array([[1, degree, -degree * h], [0, 1, 0]], dtype=np.float32)
        degree = -degree
        dstx = cv2.warpAffine(src, affx, (w + int(h * degree), h))
        # 좌표 shearing
        hw = np.array([[w, 0], [0, h]])
        ret_coord = np.reshape(ret_coord, (4, 2))
        nh, nw = dstx.shape[:2]
        degree = -degree
        r = np.dot(ret_coord, hw)
        r = [[r[0][0] + r[0][1] * degree - degree * h, r[0][1]], [r[1][0] + r[1][1] * degree - degree * h, r[1][1]],
             [r[2][0] + r[2][1] * degree - degree * h, r[2][1]], [r[3][0] + r[3][1] * degree - degree * h, r[3][1]]]
        r = [[r[0][0] / nw, r[0][1] / nh], [r[1][0] / nw, r[1][1] / nh], [r[2][0] / nw, r[2][1] / nh],
             [r[3][0] / nw, r[3][1] / nh]]
        ret_coord2 = np.array([r[0][0], r[0][1], r[1][0], r[1][1], r[2][0], r[2][1], r[3][0], r[3][1]])
        coord = self.visualization(dstx, ret_coord2)
        coord_x = []
        coord_y = []
        for i in coord:
            coord_x.append(i[0])
            coord_y.append(i[1])
        min_x = min(coord_x);
        min_y = min(coord_y)
        max_x = max(coord_x);
        max_y = max(coord_y)
        # cropped image의 padding
        pad_xr = nw * (random.uniform(0.01, 0.03))
        pad_xl = nw * (random.uniform(0.01, 0.03))
        pad_yu = nh * (random.uniform(0.005, 0.015))
        pad_yd = nh * (random.uniform(0.005, 0.015))
        cropped_img = dstx[int(min_y - pad_yu): int(min_y - pad_yu + max_y - min_y + pad_yu + pad_yd),
                      int(min_x - pad_xl): int(min_x - pad_xl + max_x - min_x + pad_xr + pad_xl)]
        coord_x = list(map(lambda x: x - int(min_x - pad_xl), coord_x))
        coord_y = list(map(lambda x: x - int(min_y - pad_yu), coord_y))
        new_coord = []
        h2, w2 = cropped_img.shape[:2]
        for i in range(4):
            new_coord.append(coord_x[i] / w2)
            new_coord.append(coord_y[i] / h2)
        return cropped_img, new_coord

    ########################################################################################################
    def original(self, input_img, ret_coord):
        src = input_img
        h, w = src.shape[:2]
        coord = self.visualization(src, ret_coord)
        coord_x = []
        coord_y = []
        for i in coord:
            coord_x.append(i[0])
            coord_y.append(i[1])
        min_x = min(coord_x);
        min_y = min(coord_y)
        max_x = max(coord_x);
        max_y = max(coord_y)
        # cropped image의 padding
        pad_xr = w * (random.uniform(0.01, 0.03))
        pad_xl = w * (random.uniform(0.01, 0.03))
        pad_yu = h * (random.uniform(0.005, 0.015))
        pad_yd = h * (random.uniform(0.005, 0.015))
        cropped_img = src[int(min_y - pad_yu): int(min_y - pad_yu + max_y - min_y + pad_yu + pad_yd),
                      int(min_x - pad_xl): int(min_x - pad_xl + max_x - min_x + pad_xr + pad_xl)]
        coord_x = list(map(lambda x: x - int(min_x - pad_xl), coord_x))
        coord_y = list(map(lambda x: x - int(min_y - pad_yu), coord_y))
        new_coord = []
        h2, w2 = cropped_img.shape[:2]
        for i in range(4):
            new_coord.append(coord_x[i] / w2)
            new_coord.append(coord_y[i] / h2)
        return cropped_img, new_coord

class AverageMeter(object):
    """Computes and stores the average and current value"""

    def __init__(self):
        self.reset()

    def reset(self):
        self.val = 0
        self.avg = 0
        self.sum = 0
        self.count = 0

    def update(self, val, n=1):
        self.val = val
        self.sum += val * n
        self.count += n
        self.avg = self.sum / self.count

def visualization(img: torch.Tensor, coord: torch.Tensor, color=(0, 255, 0)):
    input_img = np.array((img.cpu().detach().numpy().transpose((1, 2, 0)) * 255.0)).astype(
        np.uint8)  # (128, 128, 3)
    coord = coord.cpu().detach().numpy()  # (8,)

    coord_list = [(int(128 * coord[0]), int(128 * coord[1])),
                  (int(coord[2] * 128), int(128 * coord[3])),
                  (int(128 * coord[4]), int(128 * coord[5])),
                  (int(128 * coord[6]), int(128 * coord[7]))]

    image = cv2.cvtColor(np.array(input_img), cv2.COLOR_BGR2RGB)
    img = cv2.line(image, coord_list[0], coord_list[1], color=color)
    img = cv2.line(img, coord_list[1], coord_list[2], color=color)
    img = cv2.line(img, coord_list[2], coord_list[3], color=color)
    img = cv2.line(img, coord_list[3], coord_list[0], color=color)

    print("visualizing...")
    plt.imshow(img[:, :, ::-1])
    plt.show()


class ValidationDataset(Dataset):
    def __init__(self, path):
        self.img_list = glob.glob(path)

    def __len__(self):
        return len(self.img_list)

    def __getitem__(self, index):
        img_name = self.img_list[index]
        ret_coord = img_name.split('/')[-1]
        ret_coord = ret_coord.split(".jpg")[0]
        ret_coord = ret_coord.replace("__", "_")
        # Check if the ret_coord has the format "val\0.1227"
        if "\\" in ret_coord:
            ret_coord = ret_coord.split("\\")[1]  # Extract the value after the backslash

        ret_coord = np.array(list(map(float, ret_coord.split("_"))))
        ret_img = cv2.imread(img_name)

        ret_img, ret_coord = self.original(ret_img, ret_coord)
        ret_img = cv2.resize(ret_img, (128, 128)) / 255.0
        ret_img = ret_img.transpose((2, 0, 1))
        ret_coord = np.array(ret_coord)
        # ret_label = img_name.split("__")
        return ret_img, ret_coord

    def original(self, input_img, ret_coord):
        src = input_img
        h, w = src.shape[:2]
        coord = self.visualization(src, ret_coord)
        coord_x = []
        coord_y = []
        for i in coord:
            coord_x.append(i[0])
            coord_y.append(i[1])
        min_x = min(coord_x);
        min_y = min(coord_y)
        max_x = max(coord_x);
        max_y = max(coord_y)
        # cropped image의 padding
        pad_xr = w * (random.uniform(0.01, 0.03))
        pad_xl = w * (random.uniform(0.01, 0.03))
        pad_yu = h * (random.uniform(0.005, 0.015))
        pad_yd = h * (random.uniform(0.005, 0.015))
        cropped_img = src[int(min_y - pad_yu): int(min_y - pad_yu + max_y - min_y + pad_yu + pad_yd),
                      int(min_x - pad_xl): int(min_x - pad_xl + max_x - min_x + pad_xr + pad_xl)]
        coord_x = list(map(lambda x: x - int(min_x - pad_xl), coord_x))
        coord_y = list(map(lambda x: x - int(min_y - pad_yu), coord_y))
        new_coord = []
        h2, w2 = cropped_img.shape[:2]
        for i in range(4):
            new_coord.append(coord_x[i] / w2)
            new_coord.append(coord_y[i] / h2)
        return cropped_img, new_coord

    def visualization(self, input_img, coord, color=(0, 0, 255)):
        h, w = input_img.shape[:2]
        coord_list = [(int(w * coord[0]), int(h * coord[1])),
                      (int(w * coord[2]), int(h * coord[3])),
                      (int(w * coord[4]), int(h * coord[5])),
                      (int(w * coord[6]), int(h * coord[7]))]
        return coord_list

train_path = 'C:/Users/kys/capstone2023_AOS/Fitfume/AI/Alignment_Model/alignment_input/train/*.jpg'
test_path = 'C:/Users/kys/capstone2023_AOS/Fitfume/AI/Alignment_Model/alignment_input/val/*.jpg'
train_dataset = load_data(train_path)
train_loader = DataLoader(train_dataset, batch_size=64, shuffle=True, num_workers=4, pin_memory=True)

test_dataset = ValidationDataset(test_path)
test_loader = DataLoader(test_dataset, batch_size=64, num_workers=4, pin_memory=True)

# L1Loss

epoch = 256
device = torch.device('cuda')
Model.cuda()
criterion = nn.L1Loss()
optimizer = torch.optim.SGD(Model.parameters(), lr=0.05, momentum=0.9, nesterov=True)
x = torch.randn(64, 3, 128, 128, requires_grad=True).cuda()  # dummy input
best = 10000
count = 0



def run():
    best=float('inf')
    count=0
    for ep in range(epoch):
        avg_loss = AverageMeter()
        # train
        print("::::Train::::")
        Model.train()
        for idx, i in tqdm(enumerate(train_loader), total=len(train_loader)):
            data_x = i[0].type(torch.float32).cuda()
            data_y = i[1].type(torch.float32).cuda()
            tp = i[2]
            out = Model(data_x)
            out = torch.sigmoid(out)
            loss = criterion(out, data_y)
            avg_loss.update(loss.item(), data_x.shape[0])
            if ep in [1, 2, 3, 4, 5, 6, 7, 10, 20, 30, 40, 50, 80, 100, 130, 150, 170, 190, 210, 230, 255] and idx == 0:
                visualization(data_x[0], data_y[0], color=(0, 255, 0))
                visualization(data_x[0], out[0], color=(0, 0, 255))
            optimizer.zero_grad()
            loss.backward()
            optimizer.step()

        print(avg_loss.avg)
        print("::::Validation::::")
        # evaluation
        correct = 0
        total = 0
        preds = torch.tensor([], dtype=torch.float).cuda()
        gt = torch.tensor([], dtype=torch.float).cuda()
        Model.eval()
        with torch.no_grad():
            # validation code
            for idx, i in tqdm(enumerate(test_loader), total=len(test_loader)):
                data_x = i[0].type(torch.float32).cuda()  # (64, 3, 128, 128)
                data_y = i[1].type(torch.float32).cuda()  # (64, 8)

                out = Model(data_x)  # (64, 8)
                out = torch.sigmoid(out)
                preds = torch.cat((preds, out), 0)
                gt = torch.cat((gt, data_y), 0)
                if ep in [1, 10, 20, 30, 40, 50, 80, 100, 130, 150, 170, 190, 210, 230, 255] and idx == 0:
                    visualization(data_x[0], data_y[0], color=(0, 255, 0))
                    visualization(data_x[0], out[0], color=(0, 0, 255))

            preds = preds.cpu().detach().numpy()
            gt = gt.cpu().detach().numpy()
            rmse = np.sqrt(mean_squared_error(preds, gt))

        if best > rmse:
            print('smaller rmse, saving model weights...')
            print('best: ', best, 'current rmse: ', rmse)
            count += 1
            best = rmse

            # save
            p = 'C:/Users/kys/capstone2023_AOS/Fitfume/AI/Alignment_Model/resnet18_best_random_shear/best' + str(count) + '.pth'
            torch.save(Model, p)
            torch.onnx.export(Model,
                              x,
                              "C:/Users/kys/capstone2023_AOS/Fitfume/AI/Alignment_Model/onnxfile/resnet18_best_random_shear.onnx",
                              export_params=True,
                              opset_version=10,  # onnx version
                              do_constant_folding=True,
                              input_names=['input'],
                              output_names=['output'],
                              dynamic_axes={'input': {0: 'batch_size'},
                                            'output': {0: 'batch_size'}})

        print('epoch{0} end'.format(ep))
        print('=' * 100)

if __name__ == '__main__':
    run()



print('best : ' + str(best))
print('End')