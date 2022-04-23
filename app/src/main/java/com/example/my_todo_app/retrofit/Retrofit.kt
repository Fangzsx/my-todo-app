package com.example.my_todo_app.retrofit

import com.example.my_todo_app.api.ZenQuotesApi
import com.example.my_todo_app.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    val zenQuotesApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ZenQuotesApi::class.java)
    }
}