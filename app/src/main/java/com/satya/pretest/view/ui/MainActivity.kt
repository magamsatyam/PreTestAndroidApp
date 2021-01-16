package com.satya.pretest.view.ui

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.satya.pretest.R
import com.satya.pretest.databinding.ActivityMainBinding
import com.satya.pretest.di.DaggerAppComponent
import com.satya.pretest.view.adapters.AnimAdapter
import com.satya.pretest.viewmodel.AnimViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AnimViewModel by viewModels()

    @Inject
    lateinit var animAdapter: AnimAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
                    binding.recyclerView.visibility = View.VISIBLE
                    animAdapter.setUpData(it)
                    binding.emptyText.visibility = View.GONE
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
                    binding.emptyText.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
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
                    binding.emptyText.visibility = View.GONE
                    binding.fetchProgress.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeInProgress() {
        viewModel.animRepository.isInProgress.observe(this, Observer { isLoading ->
            isLoading.let {
                if (it) {
                    binding.emptyText.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.fetchProgress.visibility = View.VISIBLE
                } else {
                    binding.fetchProgress.visibility = View.GONE
                }
            }
        })
    }

    private fun populateData() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = animAdapter
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            setSearchableInfo(
                searchManager.getSearchableInfo(
                    ComponentName(
                        applicationContext,
                        SearchResultsActivity::class.java
                    )
                )
            )
        }
        searchView.setIconifiedByDefault(false)
        return true
    }

}