package com.example.shownews.repo

import com.example.shownews.api.ApiClient
import com.example.shownews.models.Article
import com.example.shownews.models.News
import retrofit2.Retrofit

class Repository {

    suspend fun getNews(): News {
        return ApiClient.api.getNews()
    }
}