import os
import matplotlib.pyplot as plt
import cv2
import numpy as np

f = open("C:/Users/kys/capstone2023_AOS/Fitfume/AI/yolov5/data/perfume/train/labels/datalist.txt")
lines = f.readlines()
# name x1 y1 x1 y2 x2 y1 x2 y2 perfume

for img in os.scandir('C:/Users/kys/capstone2023_AOS/Fitfume/AI/yolov5/data/perfume/train/images/'):
    save_name = ''

    name = img.name
    img_info = []
    for line in lines:
        splitted = line.split()
        if name == splitted[0]:
            img_info = splitted
            break
    print(img_info[0])

    # configuring path information
    img_path = os.path.join('C:/Users/kys/capstone2023_AOS/Fitfume/AI/yolov5/data/perfume/train/images/', img.name)

    # reading the image and the coordinates
    img = cv2.imread(img_path)
    h, w, c = img.shape

    # 절대좌표
    coord_list = [(float(img_info[1]), float(img_info[2])),
                  (float(img_info[3]), float(img_info[4])),
                  (float(img_info[5]), float(img_info[6])),
                  (float(img_info[7]), float(img_info[8]))]

    # 상대좌표
    lista = [(round(coord_list[0][0] / w, 4), round(coord_list[0][1] / h, 4)),
             (round(coord_list[1][0] / w, 4), round(coord_list[1][1] / h, 4)),
             (round(coord_list[2][0] / w, 4), round(coord_list[2][1] / h, 4)),
             (round(coord_list[3][0] / w, 4), round(coord_list[3][1] / h, 4))]

    save_name = str(lista[0][0]) + '_' + str(lista[0][1]) + '__' + str(lista[1][0]) + '_' + str(
        lista[1][1]) + '__' + str(lista[2][0]) + '_' + str(lista[2][1]) + '__' + str(lista[3][0]) + '_' + str(
        lista[3][1]) + '.jpg'

    print(save_name)

    save_path = os.path.join('C:/Users/kys/capstone2023_AOS/Fitfume/AI/Alignment_Model/alignment_input/train/', save_name)
    cv2.imwrite(save_path, img)