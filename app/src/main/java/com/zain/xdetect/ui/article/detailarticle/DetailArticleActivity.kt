package com.zain.xdetect.ui.article.detailarticle

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.zain.xdetect.R
import com.zain.xdetect.data.remote.model.detailarticle.DataDetailArticle
import com.zain.xdetect.databinding.ActivityDetailArticleBinding
import com.zain.xdetect.ui.article.ArticleViewModel


class DetailArticleActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailArticleBinding
    private val articleViewModel: ArticleViewModel by viewModels()

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

        articleViewModel.getDetailArticle(id = id!!)

        articleViewModel.detailArticle.observe(this) { detailArticle ->
            setDataArticle(detailArticle)
        }

        articleViewModel.isLoading.observe(this) {
            showLoading(it)
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
            tvTitleArticle.text = detailArticle.title

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDescArticle.text =
                    Html.fromHtml(detailArticle.content, Html.FROM_HTML_MODE_COMPACT)
            } else {
                tvDescArticle.text =
                    HtmlCompat.fromHtml(detailArticle.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }

//            tvDescArticle.text = detailArticle.content

        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
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