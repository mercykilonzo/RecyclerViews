package com.classwork.recyclerviews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.classwork.recyclerviews.model.Post
import com.classwork.recyclerviews.repository.PostRepository
import kotlinx.coroutines.launch

class PostsViewModel : ViewModel() {
    val postsLiveData = MutableLiveData<List<Post>>()
    val errorLiveData = MutableLiveData<String>()
    val postsRepository = PostRepository()

    fun fetchPost(){
        viewModelScope.launch {
            val response = postsRepository.fetchPosts()
            if (response.isSuccessful) {
                postsLiveData.postValue(response.body())
            }else{
                errorLiveData.postValue(response.errorBody()?.string())
            }
        }
    }
}