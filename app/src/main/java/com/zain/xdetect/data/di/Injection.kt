package com.zain.xdetect.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zain.xdetect.data.local.preference.UserPreference
import com.zain.xdetect.data.remote.api.ApiConfig
import com.zain.xdetect.data.remote.repository.DetectionRepository
import com.zain.xdetect.data.remote.repository.UserRepository


object Injection {

    fun provideUserRepository(dataStore: DataStore<Preferences>): UserRepository {
        val userApiService = ApiConfig.getApiService()
        val authPreferences = UserPreference.getInstance(dataStore)
        return UserRepository.getInstance(userApiService, authPreferences)
    }

    fun provideDetectionRepository(): DetectionRepository {
        val apiService = ApiConfig.getDetectionApiService()
        return DetectionRepository.getInstance(apiService)
    }

//    fun provideStoryRepository(
//        context: Context,
//        dataStore: DataStore<Preferences>
//    ): StoryRepository {
//        val apiService = ApiConfig.getUserApiService()
//        val userPreference = UserPreference.getInstance(dataStore)
//        val database = StoryDatabase.getInstance(context)
//        return StoryRepository.getInstance(apiService, userPreference, database)
//    }
}