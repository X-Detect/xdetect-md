package com.zain.xdetect.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zain.xdetect.data.remote.repository.ArticleRepository

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {
    fun getListArticle() = articleRepository.getListArticle().asLiveData()

    fun getDetailArticle(id:String) = articleRepository.getDetailArticle(id).asLiveData()

    companion object {
        private const val TAG = "ArticleViewModel"
    }
}