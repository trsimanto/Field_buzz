package com.towhid.fieldbuzz.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private val BASE_URL ="https://recruitment.fisdev.com/api/"

    companion object {
        private var mInstance: RetrofitClient? = null

        private var retrofit: Retrofit? = null
        @Synchronized
        public fun getInstance(): RetrofitClient {
            if (mInstance == null)
                mInstance =
                        RetrofitClient()
            return mInstance as RetrofitClient
        }
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    fun getApi(): Api {
        return retrofit!!.create(Api::class.java)
    }

    fun getRetrofit(): Retrofit {
        return retrofit!!
    }


}