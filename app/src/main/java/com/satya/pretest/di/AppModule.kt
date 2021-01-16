package com.satya.pretest.di


import com.satya.pretest.data.remote.AnimeApi
import com.satya.pretest.data.remote.AnimeApiService
import com.satya.pretest.repository.AnimRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.satya.pretest.model.Result
import com.satya.pretest.view.adapters.AnimAdapter

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): AnimeApi = AnimeApiService.getClient()

    @Provides
    fun provideTrendingRepository() = AnimRepository()

    @Provides
    fun provideListData() = ArrayList<Result>()

    @Provides
    fun provideTrendingAdapter(data: ArrayList<Result>): AnimAdapter = AnimAdapter(data)
}