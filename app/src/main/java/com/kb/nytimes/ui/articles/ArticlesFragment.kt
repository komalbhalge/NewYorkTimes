package com.kb.nytimes.ui.articles

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kb.nytimes.R
import com.kb.nytimes.databinding.ArticlesFragmentOneBinding
import com.kb.nytimes.ui.ArticlesViewModel
import com.kb.nytimes.ui.adapter.ArticlesAdaptor
import com.kb.nytimes.ui.base.BaseFragment
import com.kb.nytimes.util.Constants.ARTICLE_TYPE_KEY
import com.kb.nytimes.util.Constants.VIEWED
import com.kb.nytimes.util.hide
import com.kb.nytimes.util.show

class ArticlesFragment : BaseFragment<ArticlesFragmentOneBinding, ArticlesViewModel>() {

    lateinit var navContoller: NavController
    override val viewModel: ArticlesViewModel by activityViewModels()
    private lateinit var newsAdapter: ArticlesAdaptor
    private lateinit var articleType: String

    private var period = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleType = arguments?.getString(ARTICLE_TYPE_KEY) ?: VIEWED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navContoller = Navigation.findNavController(view)
        setHasOptionsMenu(true)
        setup()
    }

    private fun setup() = with(binding) {
        initArticlesRv()
        observeNetworkConnectivity()
        viewModel.loadArticles(articleType, period)
        observeArticles()
        swipeToRefreshArticles()
    }

    private fun initArticlesRv() = with(binding) {
        // init article rv
        newsAdapter = ArticlesAdaptor().also {
            articleRv.adapter = it
        }
    }

    private fun swipeToRefreshArticles() = with(binding) {
        refreshArticles.setOnRefreshListener {
            viewModel.loadArticles(articleType, period)
        }
    }

    private fun observeArticles() = with(binding) {
        // observe the articles
        with(viewModel) {
            articlesList.observe(viewLifecycleOwner) {
                refreshArticles.isRefreshing = false
                newsAdapter.differ.submitList(it.results)
                articleRv.animate().alpha(1f)
                articleRv.itemAnimator = null // prevents flickering between article categories
            }
            toolbarLabel.observe(viewLifecycleOwner) {
                (activity as AppCompatActivity?)!!.supportActionBar?.setTitle(getString(it))
            }

        }
    }

    private fun observeNetworkConnectivity() {
        viewModel.shouldShowNoInternetAlert.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) onConnectivityAvailable() else onConnectivityUnavailable()
        }
    }

    private fun onConnectivityAvailable() = with(binding) {
        textNetworkStatus.apply {
            text = getString(R.string.text_connectivity)
        }
        containerNetworkStatus.apply {
            setBackgroundColor(
                getColor(context, R.color.colorStatusConnected)
            )
            animate()
                .alpha(1f)
                .setStartDelay(ANIMATION_DURATION)
                .setDuration(ANIMATION_DURATION)
                .setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            hide()
                        }
                    }
                )
                .start()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun onConnectivityUnavailable() = with(binding) {
        textNetworkStatus.apply {
            text = getString(R.string.text_no_connectivity)
        }
        containerNetworkStatus.apply {
            show()
            setBackgroundColor(
                getColor(context, R.color.colorStatusNotConnected)
            )
        }
    }

    companion object {
        const val ANIMATION_DURATION = 3000L
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ArticlesFragmentOneBinding.inflate(inflater, container, false)
}