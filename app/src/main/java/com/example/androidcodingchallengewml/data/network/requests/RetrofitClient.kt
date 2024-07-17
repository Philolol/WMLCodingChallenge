package com.example.androidcodingchallengewml.data.network.requests

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_BASE_URL = "https://gist.githubusercontent.com"

class RetrofitClient {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .addInterceptor{ chain ->
                    val newRequest: Request = chain.request().newBuilder()
                        .build()
                    chain.proceed(newRequest)
                }
                .build()
            Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}