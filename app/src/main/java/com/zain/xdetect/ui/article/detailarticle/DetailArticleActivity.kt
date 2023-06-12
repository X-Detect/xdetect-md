package com.zain.xdetect.ui.article.detailarticle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.zain.xdetect.data.local.model.Result
import com.bumptech.glide.Glide
import com.zain.xdetect.R
import com.zain.xdetect.data.remote.model.detailarticle.DataDetailArticle
import com.zain.xdetect.databinding.ActivityDetailArticleBinding
import com.zain.xdetect.databinding.FragmentArticleBinding
import com.zain.xdetect.ui.article.ArticleViewModel
import com.zain.xdetect.ui.article.ArticleViewModelFactory


class DetailArticleActivity : AppCompatActivity(), View.OnClickListener {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private lateinit var binding: ActivityDetailArticleBinding
    private val articleViewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory.getInstance(this, dataStore)
    }

    private var id: String? = null
    private var detailArticle: DataDetailArticle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

        getData()
    }

    private fun getData() {
        id = intent.getStringExtra(ID_ARTICLE)

        articleViewModel.getDetailArticle(id = id!!).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    detailArticle = result.data
                    setDataArticle(detailArticle!!)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Failed to load article",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


    private fun setToolbar() {
        binding.myToolbar.btnBackToolbar.setOnClickListener(this)
        binding.myToolbar.tvToolbarTitle.text = "Article Detail"
    }

    private fun setDataArticle(detailArticle: DataDetailArticle) {
        binding.apply {
            Glide.with(root)
                .load(detailArticle.imageURL)
                .into(imgArticle)
            tvProfile.text = detailArticle.createdBy
            tvTitleArticle.text = detailArticle.title

            tvDescArticle.text = Html.fromHtml(detailArticle.content, Html.FROM_HTML_MODE_COMPACT)

            tvLink.text = detailArticle.sourceURL
        }
    }

    companion object {
        const val ID_ARTICLE = "default_id"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_back_toolbar -> {
                finish()
            }
        }
    }
}