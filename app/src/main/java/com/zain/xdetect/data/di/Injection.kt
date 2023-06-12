package com.zain.xdetect.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zain.xdetect.data.local.preference.UserPreference
import com.zain.xdetect.data.remote.api.ApiConfig
import com.zain.xdetect.data.remote.repository.ArticleRepository
import com.zain.xdetect.data.remote.repository.DetectionRepository
import com.zain.xdetect.data.remote.repository.UserRepository


object Injection {

    fun provideUserRepository(dataStore: DataStore<Preferences>): UserRepository {
        val userApiService = ApiConfig.getApiService()
        val authPreferences = UserPreference.getInstance(dataStore)
        return UserRepository.getInstance(userApiService, authPreferences)
    }

    fun provideArticleRepository(): ArticleRepository {
        val apiService = ApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }

    fun provideDetectionRepository(): DetectionRepository {
        val apiService = ApiConfig.getDetectionApiService()
        return DetectionRepository.getInstance(apiService)
    }
}