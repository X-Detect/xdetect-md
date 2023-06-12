package com.zain.xdetect.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zain.xdetect.data.local.model.Result
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.xdetect.data.remote.model.article.ArticleItem
import com.zain.xdetect.data.remote.model.auth.DataUser
import com.zain.xdetect.databinding.FragmentArticleBinding
import com.zain.xdetect.ui.article.detailarticle.DetailArticleActivity
import com.zain.xdetect.ui.article.adapter.ListArticleAdapter
import com.zain.xdetect.ui.auth.AuthViewModel
import com.zain.xdetect.ui.auth.AuthViewModelFactory

class ArticleFragment : Fragment() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val articleViewModel: ArticleViewModel by viewModels{
        ArticleViewModelFactory.getInstance(requireContext(),requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel.getListArticle().observe(requireActivity()) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val listArticle = result.data
                    setArticleData(listArticle)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireActivity(),
                        "Failed to load article",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun setArticleData(listArticle: List<ArticleItem>) {
        val adapter = ListArticleAdapter(listArticle)

        adapter.setOnItemClickCallback(object : ListArticleAdapter.OnItemClickCallback {
            override fun onItemArticleClicked(item: ArticleItem) {
                navigateToDetailArticle(item)
            }
        })

        binding.apply {
            rvArticle.adapter = adapter

            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvArticle.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
            binding.rvArticle.addItemDecoration(itemDecoration)
        }

    }

    private fun navigateToDetailArticle(article: ArticleItem) {
        val moveToDetail = Intent(requireActivity(), DetailArticleActivity::class.java)
        moveToDetail.putExtra(DetailArticleActivity.ID_ARTICLE, article.id)
        startActivity(moveToDetail)
    }
}