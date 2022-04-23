package com.example.my_todo_app.api

import com.example.my_todo_app.model.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET


interface ZenQuotesApi {


    @GET("api/random")
    suspend fun getRandomQuote() : Response<QuoteResponse>

}