package com.zain.xdetect.ui.article

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zain.xdetect.data.di.Injection
import com.zain.xdetect.data.remote.repository.ArticleRepository

class ArticleViewModelFactory(
    private val articleRepository: ArticleRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
                ArticleViewModel(articleRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ArticleViewModelFactory? = null
        fun getInstance(
            context: Context,
            dataStore: DataStore<Preferences>
        ): ArticleViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ArticleViewModelFactory(
                    Injection.provideArticleRepository()
                )
            }.also { instance = it }
    }
}