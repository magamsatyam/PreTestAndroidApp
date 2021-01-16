package com.satya.pretest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.satya.data.database.toDataEntityList
import com.example.satya.data.database.toDataList
import com.satya.pretest.PreTestApplication
import com.satya.pretest.data.remote.AnimeApi
import com.satya.pretest.di.DaggerAppComponent
import com.satya.pretest.model.AnimeResult
import com.satya.pretest.model.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Repository class which stores data to the database and
 * provides observables back to UI.
 */
class AnimRepository {

    @Inject
    lateinit var animeApiService: AnimeApi

    private val _data by lazy { MutableLiveData<List<Result>>() }
    val data: LiveData<List<Result>>
        get() = _data

    private val _isInProgress by lazy { MutableLiveData<Boolean>() }
    val isInProgress: LiveData<Boolean>
        get() = _isInProgress

    private val _isError by lazy { MutableLiveData<Boolean>() }
    val isError: LiveData<Boolean>
        get() = _isError

    init {
        DaggerAppComponent.create().inject(this)
    }

    private fun insertData(key: String): Disposable {
        return animeApiService.getAnimeList(key)
            .subscribeOn(Schedulers.io())
            .subscribeWith(subscribeToDatabase(key))
    }

    private fun fetchFromNetwork(key: String): Disposable {
        return animeApiService.getAnimeList(key)
            .subscribeOn(Schedulers.io())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribeWith(object : DisposableSubscriber<AnimeResult>() {
                override fun onNext(animeResult:AnimeResult) {
                    val entityList = animeResult.results.toList()
                    Log.v("List", entityList.toString());
                    _data.postValue(animeResult.results.toList())
                }

                override fun onError(t: Throwable) {
                    _isInProgress.postValue(true)
                    Log.e("insertData()", "AnimeResult error: ${t?.message}")
                    _isError.postValue(true)
                    _isInProgress.postValue(false)
                }

                override fun onComplete() {
                    Log.v("insertData()", "insert success")

                }
            })
    }

    private fun subscribeToDatabase(key: String): DisposableSubscriber<AnimeResult> {
        return object : DisposableSubscriber<AnimeResult>() {

            override fun onNext(animeResult: AnimeResult?) {
                if (animeResult != null) {
                    val entityList = animeResult.results.toList().toDataEntityList()
                    Log.v("List", entityList.toString());
                    PreTestApplication.database.apply {
                        dataDao().insertData(entityList)
                    }
                }
            }

            override fun onError(t: Throwable?) {
                _isInProgress.postValue(true)
                Log.e("insertData()", "AnimeResult error: ${t?.message}")
                _isError.postValue(true)
                _isInProgress.postValue(false)
            }

            override fun onComplete() {
                Log.v("insertData()", "insert success")
                getAnimData(key)
            }
        }
    }

    private fun getAnimData(key: String): Disposable {
        return PreTestApplication.database.dataDao()
            .queryData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { dataEntityList ->
                    _isInProgress.postValue(true)
                    if (dataEntityList != null && dataEntityList.isNotEmpty()) {
                        _isError.postValue(false)
                        _data.postValue(dataEntityList.toDataList())
                    } else {
                        insertData(key)
                    }
                    _isInProgress.postValue(false)
                },
                {
                    _isInProgress.postValue(true)
                    Log.e("getAnimData", "Database error: ${it.message}")
                    _isError.postValue(true)
                    _isInProgress.postValue(false)
                }
            )
    }

    fun fetchDataFromDatabase(key: String): Disposable = getAnimData(key)
    fun fetchDataFromRemote(key: String): Disposable = fetchFromNetwork(key)
}