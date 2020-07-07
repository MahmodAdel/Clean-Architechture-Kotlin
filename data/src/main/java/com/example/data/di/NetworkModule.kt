package com.example.data.di

import android.app.Application
import android.content.Context
import com.example.data.local.db.AppDatabase
import com.example.data.local.db.dao.CarDao
import com.example.data.remote.api.CarApi
import com.example.data.remote.builder.RetrofitBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule{
    @Provides
    @Singleton
    fun provideRetrofit(retrofitBuilder: RetrofitBuilder): Retrofit = retrofitBuilder
        .build()

    @Provides
    @Singleton
    fun provideContributorApi(retrofit: Retrofit): CarApi = retrofit.create(CarApi::class.java)



    @Provides
    @Singleton
    fun provideAppDatabase(app : Context): CarDao{
        return AppDatabase.getDatabase(app).carDao()
    }





}