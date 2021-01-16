package com.satya.pretest.data.remote

import com.satya.pretest.model.AnimeResult
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * the functions in this interface represents individual service calls
 * and annotated with request types like GET, POST, PUT and DELETE
 * function arguments are for sending the data to service in payload.
 */
interface AnimeApi {

    @GET("v3/search/anime")
    fun getAnimeList(
      @Query("q") searchKey: String
    ): Flowable<AnimeResult>
}