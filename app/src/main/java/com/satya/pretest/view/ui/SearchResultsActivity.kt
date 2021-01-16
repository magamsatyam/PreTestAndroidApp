package com.satya.pretest.view.ui

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.satya.pretest.databinding.ActivitySearchResultsBinding
import com.satya.pretest.di.DaggerAppComponent
import com.satya.pretest.view.adapters.AnimAdapter
import com.satya.pretest.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchResultsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchResultsBinding
    private val viewModel: SearchViewModel by viewModels()
    @Inject
    lateinit var animAdapter: AnimAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater);
        val view = binding.root
        setContentView(view)
        handleSearchIntent(intent)
        DaggerAppComponent.create().inject(this)

        populateData()
        observeLiveData()
    }

    private fun observeLiveData() {
        observeInProgress()
        observeIsError()
        observeGiphyList()
    }

    private fun observeGiphyList() {
        viewModel.animRepository.data.observe(this, Observer { anime ->
            anime.let {
                if (it != null && it.isNotEmpty()) {
                    binding.fetchProgress.visibility = View.VISIBLE
                    binding.searchRecyclerView.visibility = View.VISIBLE
                    animAdapter.setUpData(it)
                    binding.searchEmptyText.visibility = View.GONE
                    binding.fetchProgress.visibility = View.GONE
                } else {
                    disableViewsOnError()
                }
            }
        })
    }

    private fun disableViewsOnError() {
        viewModel.animRepository.isInProgress.observe(this, Observer { isLoading ->
            isLoading.let {
                if (it) {
                    binding.searchEmptyText.visibility = View.GONE
                    binding.searchRecyclerView.visibility = View.GONE
                    binding.fetchProgress.visibility = View.VISIBLE
                } else {
                    binding.fetchProgress.visibility = View.GONE
                }
            }
        })
    }

    private fun observeIsError() {
        viewModel.animRepository.isError.observe(this, Observer { isError ->
            isError.let {
                if (it) {
                    disableViewsOnError()
                } else {
                    binding.searchEmptyText.visibility = View.GONE
                    binding.fetchProgress.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeInProgress() {
        viewModel.animRepository.isInProgress.observe(this, Observer { isLoading ->
            isLoading.let {
                if (it) {
                    binding.searchEmptyText.visibility = View.GONE
                    binding.searchRecyclerView.visibility = View.GONE
                    binding.fetchProgress.visibility = View.VISIBLE
                } else {
                    binding.fetchProgress.visibility = View.GONE
                }
            }
        })
    }

    private fun populateData() {
        binding.searchRecyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = animAdapter
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    private fun handleSearchIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d("SearchActivity","${query}")
            viewModel.fetchDataForSearchKey(query.toString())
        }
    }
}