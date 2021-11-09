package com.kb.nytimes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kb.nytimes.R
import com.kb.nytimes.databinding.SearchFragmentBinding
import com.kb.nytimes.ui.adapter.SearchedNewsAdaper
import com.kb.nytimes.ui.base.BaseFragment
import com.kb.nytimes.util.hide
import com.kb.nytimes.util.show
import kotlinx.android.synthetic.main.searched_news_fragment.*

class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>(),
    AdapterView.OnItemSelectedListener {
    var navController: NavController? = null
    override val viewModel: SearchViewModel by activityViewModels()
    private var searchListBinding: SearchFragmentBinding? = null
    private lateinit var listAdapter: SearchedNewsAdaper //{ position -> onListItemClick(position) }
    private var searchKey: String = DEFAULT_SEARCH
    private var selectedSource: String = DEFAULT_SEARCH

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val binding = SearchFragmentBinding.bind(view)
        searchListBinding = binding
        binding.buttonSearch.setOnClickListener(clickListener)
        binding.etSearch.setOnClickListener(clickListener)
        initRecyclerView()
        observeData()
    }

    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.button_search -> handleSearchButtonClick()
            R.id.et_search -> handleSearchTextClick()
        }
    }

    private fun handleSearchTextClick() {
        with(binding.buttonSearch) {
            show()
        }
    }


    private fun handleSearchButtonClick() {
        //Show search result list here and onclick of list item navigate to detail screen
        searchKey = binding.etSearch.text?.toString() ?: DEFAULT_SEARCH
        progressbarVisibility(true)
        viewModel.loadArticles(searchKey)
        with(binding.buttonSearch) {
            hide()
        }
    }

    private fun initRecyclerView() {
        listAdapter = SearchedNewsAdaper().also {
            articlesRecyclerView.adapter = it
        }
        empty_state.visibility = View.VISIBLE
        tv_empty_search.text = getString(R.string.search_result)

    }

    private fun observeData() {
        with(viewModel) {
            articlesList.observe(viewLifecycleOwner) {
                it?.let { response ->
                    progressbarVisibility(false)
                    if (response.isEmpty()) {
                        with(empty_state) {
                            show()
                        }
                        with(scrollView) {
                            hide()
                        }
                        tv_empty_search.text = getString(R.string.no_result)
                    } else {
                        with(empty_state) {
                            hide()
                        }
                        with(scrollView) {
                            show()
                        }
                        listAdapter.differ.submitList(response)
                        listAdapter.notifyDataSetChanged()
                        Log.v("GET", "" + it.size)
                    }
                }
            }

            //No connection pop-up
            shouldShowNoInternetAlert.observe(viewLifecycleOwner) {
                if (it) {
                    context?.let { it1 -> showNoConnectionPopup(it1) }
                }
            }

            shouldShowApiErrorAlert.observe(viewLifecycleOwner) { error ->
                context?.let { showPopup(it, message = error.message) }
            }
        }
    }

    private fun progressbarVisibility(isVisible: Boolean) {
        if (isVisible) {
            progressbar.visibility = View.VISIBLE
        } else {
            progressbar.visibility = View.GONE
        }
    }

    companion object {
        private const val DEFAULT_SEARCH = ""
        private const val DEFAULT_YEAR = ""
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedSource = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        searchKey = DEFAULT_YEAR
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = SearchFragmentBinding.inflate(inflater, container, false)
}