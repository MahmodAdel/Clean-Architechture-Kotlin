package com.example.data.remote.api

import com.example.data.model.CarEntity
import com.example.data.remote.response.DataRepoResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApi{
    @GET("photos")
    fun getResponse(
        @Query("albumId") page: Int
    ): Single<List<CarEntity>?>?
}