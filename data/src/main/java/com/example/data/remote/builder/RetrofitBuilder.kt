package com.example.data.remote.builder

import android.content.Context
import com.example.cleanarchitecture.data.remote.factory.RxErrorHandlingCallAdapterFactory
import com.example.data.HttpClient
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitBuilder @Inject constructor(private val context: Context){
    private var connectionTimeout = HttpClient.CONNECT_TIMEOUT
    private var writeTimeout = HttpClient.WRITE_TIMEOUT
    private var readTimeout = HttpClient.READ_TIMEOUT
    private var okHttpClientBuilder: OkHttpClient.Builder? = null
    private var  baseUrl:String="https://jsonplaceholder.typicode.com/"

    fun setBaseURL(baseUrl: String): RetrofitBuilder {
        this.baseUrl = baseUrl
        return this
    }

    fun build(): Retrofit {
        val clientBuilder = okHttpClientBuilder?.let { it } ?: OkHttpClient.Builder().apply {
            connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            writeTimeout(writeTimeout, TimeUnit.SECONDS)
            readTimeout(readTimeout, TimeUnit.SECONDS)

        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientBuilder.build())
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}