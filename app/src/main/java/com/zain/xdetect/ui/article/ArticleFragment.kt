package com.zain.xdetect.ui.article

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.xdetect.data.remote.model.article.ArticleItem
import com.zain.xdetect.databinding.FragmentArticleBinding
import com.zain.xdetect.ui.article.detailarticle.DetailArticleActivity
import com.zain.xdetect.ui.article.adapter.ListArticleAdapter

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel.listArticle.observe(viewLifecycleOwner) { listArticle ->
            setArticleData(listArticle)
        }
        articleViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
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

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun navigateToDetailArticle(article: ArticleItem) {
        val moveToDetail =Intent(requireActivity(), DetailArticleActivity::class.java)
        moveToDetail.putExtra(DetailArticleActivity.ID_ARTICLE,article.id)
        startActivity(moveToDetail)
    }
}