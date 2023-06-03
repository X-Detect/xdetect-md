package com.zain.myapplication.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.zain.myapplication.data.local.preference.UserPreference
import com.zain.myapplication.data.remote.api.ApiConfig
import com.zain.myapplication.data.remote.repository.UserRepository


object Injection {

    fun provideUserRepository(dataStore: DataStore<Preferences>): UserRepository {
        val userApiService = ApiConfig.getApiService()
        val authPreferences = UserPreference.getInstance(dataStore)
        return UserRepository.getInstance(userApiService, authPreferences)
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