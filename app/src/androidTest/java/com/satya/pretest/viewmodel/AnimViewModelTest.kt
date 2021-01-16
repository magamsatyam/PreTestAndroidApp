package com.satya.pretest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.satya.pretest.PreTestApplication
import com.satya.pretest.data.remote.AnimeApi
import com.satya.pretest.internal.KEY
import com.satya.pretest.model.AnimeResult
import com.satya.pretest.repository.AnimRepository
import junit.framework.TestCase
import kotlinx.coroutines.newSingleThreadContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import com.satya.pretest.model.Result
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class AnimViewModelTest {


    @Mock
    lateinit var dataObserver: Observer<List<Result>>
    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var animRepository: AnimRepository

    @Mock
    lateinit var animeApi: AnimeApi

//    @Mock
//    lateinit var application: PreTestApplication
    private lateinit var animViewModel: AnimViewModel
    private val threadContext = newSingleThreadContext("UI thread")
    private val animList : List<Result> = listOf()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(anyObject())
       animViewModel = AnimViewModel()
    }

    @Test
    fun testGetAnimRepository() {
        //Assemble

        //Act
        animViewModel.animRepository.data.observeForever(dataObserver)
        animViewModel.animRepository.isInProgress.observeForever(loadingObserver)
        animViewModel.animRepository.fetchDataFromDatabase(KEY)

        Thread.sleep(1000)

        //Verify
        verify(loadingObserver).onChanged(true)
        verify(dataObserver).onChanged(animList)
        verify(loadingObserver).onChanged(false)


    }


    @After
    fun tearDown() {
        threadContext.close()
    }

    private fun <T> anyObject(): T {
        Mockito.anyObject<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T


}