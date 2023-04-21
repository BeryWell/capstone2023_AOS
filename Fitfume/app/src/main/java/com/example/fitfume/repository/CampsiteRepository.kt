package com.example.fitfume.repository

import com.example.fitfume.model.FitfumeEventBanner
import com.example.fitfume.model.data.fakeEventBannerItemList

interface CampsiteRepository {
    suspend fun getCampsiteEventBannerItems(): List<FitfumeEventBanner>
}

class CampsiteRepositoryImpl : CampsiteRepository {
    override suspend fun getCampsiteEventBannerItems(): List<FitfumeEventBanner> {
        return fakeEventBannerItemList
    }
}