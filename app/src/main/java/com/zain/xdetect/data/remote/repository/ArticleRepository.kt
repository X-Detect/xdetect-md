package com.zain.xdetect.data.remote.repository

import android.util.Log
import com.zain.xdetect.data.remote.api.ApiService
import com.zain.xdetect.data.remote.model.article.ArticleItem
import com.zain.xdetect.data.remote.model.detailarticle.DataDetailArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.zain.xdetect.data.local.model.Result

class ArticleRepository private constructor(
    private val articleApiService: ApiService
) {

    fun getListArticle(): Flow<Result<List<ArticleItem>>> = flow {
        emit(Result.Loading)
        try {
            val response = articleApiService.getAllArticle()
            emit(Result.Success(response.data))
        } catch (e: Exception) {
            Log.d("ArticleRepository", "getListArticle: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailArticle(id: String): Flow<Result<DataDetailArticle>> = flow {
        emit(Result.Loading)
        try {
            val response = articleApiService.getDetailArticle(id)
            emit(Result.Success(response.data))
        } catch (e: Exception) {
            Log.d("ArticleRepository", "getDetailArticle: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ArticleRepository = instance ?: synchronized(this) {
            instance ?: ArticleRepository(apiService)
        }.also { instance = it }
    }
}