package com.kb.nytimes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kb.nytimes.R
import com.kb.nytimes.databinding.FragmentHomeBinding
import com.kb.nytimes.ui.base.BaseFragment
import com.kb.nytimes.util.Constants.ARTICLE_TYPE_KEY
import com.kb.nytimes.util.Constants.EMAILED
import com.kb.nytimes.util.Constants.SHARED
import com.kb.nytimes.util.Constants.VIEWED

class HomeFragment : BaseFragment<FragmentHomeBinding, ArticlesViewModel>() {

    override val viewModel: ArticlesViewModel by activityViewModels()
    lateinit var navContoller: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navContoller = Navigation.findNavController(view)
        with(binding) {
            btnSearch.setOnClickListener { onClick(it) }
            btnViewed.setOnClickListener { onClick(it) }
            btnMostShared.setOnClickListener { onClick(it) }
            btnMostEmailed.setOnClickListener { onClick(it) }
        }
    }
    
    fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_search -> {
                navContoller.navigate(R.id.action_home_to_searchFragment)
            }
            R.id.btn_viewed -> {
                val bundle = bundleOf(ARTICLE_TYPE_KEY to VIEWED)
                navContoller.navigate(R.id.action_home_to_articlesFragment, bundle)
            }
            R.id.btn_most_shared -> {
                val bundle = bundleOf(ARTICLE_TYPE_KEY to SHARED)
                navContoller.navigate(R.id.action_home_to_articlesFragment, bundle)
            }
            R.id.btn_most_emailed -> {
                val bundle = bundleOf(ARTICLE_TYPE_KEY to EMAILED)
                navContoller.navigate(R.id.action_home_to_articlesFragment, bundle)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)
}