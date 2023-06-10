package com.zain.xdetect.ui.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zain.xdetect.data.remote.api.ApiConfig
import com.zain.xdetect.data.remote.model.article.ArticleItem
import com.zain.xdetect.data.remote.model.article.ArticleResponse
import com.zain.xdetect.data.remote.model.detailarticle.DataDetailArticle
import com.zain.xdetect.data.remote.model.detailarticle.DetailArticleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel() {
    private val _listArticle = MutableLiveData<List<ArticleItem>>()
    val listArticle: LiveData<List<ArticleItem>> = _listArticle

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailArticle = MutableLiveData<DataDetailArticle>()
    val detailArticle: LiveData<DataDetailArticle> = _detailArticle

    init {
        getListArticle()
    }

    fun getListArticle() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllArticle()
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val newArticleList =
                            _listArticle.value?.plus(responseBody.data) ?: responseBody.data
                        _listArticle.postValue(newArticleList)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getDetailArticle(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailArticle(id = id)
        client.enqueue(object : Callback<DetailArticleResponse> {
            override fun onResponse(
                call: Call<DetailArticleResponse>,
                response: Response<DetailArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailArticle.value = responseBody.data!!
                    }
                    Log.i(TAG, "OKAYU DETAIL $_detailArticle")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<DetailArticleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ArticleViewModel"
    }
}