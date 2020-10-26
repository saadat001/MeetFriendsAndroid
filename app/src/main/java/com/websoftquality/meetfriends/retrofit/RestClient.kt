package com.websoftquality.meetfriends.retrofit

import com.google.gson.GsonBuilder
import com.websoftquality.meetfriends.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    companion object {
        private var retrofit: Retrofit? = null

        private val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build()

        private val imageHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC })
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS).build()

        fun getClient(): API {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
            return retrofit!!.create(API::class.java)
        }
        var gson = GsonBuilder()
            .setLenient()
            .create()

        fun getUploadClient(): API {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(imageHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit!!.create(API::class.java)
        }

    }
}