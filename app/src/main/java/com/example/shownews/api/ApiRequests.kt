package com.example.shownews.api

import com.example.shownews.models.Article
import com.example.shownews.models.News
import retrofit2.http.GET

interface ApiRequests {

    @GET("top-headlines?country=in&category=business&apiKey=36e1f801dcdf4bc0a178c692ba67e2b9")
    suspend fun getNews(): News
}