package com.goodwy.dialer.spamdetection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://i-dialer-ai-backend.onrender.com"

    val spamApi: SpamApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpamApi::class.java)
    }
}
