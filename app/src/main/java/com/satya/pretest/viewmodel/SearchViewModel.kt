package com.satya.pretest.viewmodel

import androidx.lifecycle.ViewModel
import com.satya.pretest.di.DaggerAppComponent
import com.satya.pretest.internal.KEY
import com.satya.pretest.repository.AnimRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * View model class which communicates with the SearchResultsActivity
 * and gives reference by observables.
 */
class SearchViewModel() : ViewModel() {

    @Inject
    lateinit var animRepository: AnimRepository

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        DaggerAppComponent.create().inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun fetchDataForSearchKey(key: String){
        compositeDisposable.add(animRepository.fetchDataFromRemote(key))
    }
}