package com.satya.pretest.di

import com.satya.pretest.repository.AnimRepository
import com.satya.pretest.view.adapters.AnimViewHolder
import com.satya.pretest.view.ui.MainActivity
import com.satya.pretest.view.ui.SearchResultsActivity
import com.satya.pretest.viewmodel.AnimViewModel
import com.satya.pretest.viewmodel.SearchViewModel
import dagger.Component
import javax.inject.Singleton


/**
 * App component class for dependency injection
 * Dagger will create factory classes at the time of compilation.
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(animRepository: AnimRepository)

    fun inject(animViewModel: AnimViewModel)
    fun inject(searchViewModel: SearchViewModel)

    fun inject(mainActivity: MainActivity)
    fun inject(searchResultsActivity: SearchResultsActivity)
}