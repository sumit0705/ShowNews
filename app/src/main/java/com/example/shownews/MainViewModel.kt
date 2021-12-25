package com.example.shownews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shownews.models.Article
import com.example.shownews.models.News
import com.example.shownews.repo.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository): ViewModel() {

    val myResponse: MutableLiveData<News> = MutableLiveData()

    fun getNews(){
        viewModelScope.launch {
            val response: News = repo.getNews()
            myResponse.value = response
        }
    }



}