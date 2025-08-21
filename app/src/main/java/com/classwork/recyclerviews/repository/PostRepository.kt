package com.classwork.recyclerviews.repository

import com.classwork.recyclerviews.api.ApiClient
import com.classwork.recyclerviews.api.ApiInterface
import com.classwork.recyclerviews.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PostRepository {
    val retrofit  = ApiClient.buildApiClient(ApiInterface::class.java)

    suspend fun fetchPosts(): Response<List<Post>> {
        return  withContext(Dispatchers.IO){
            retrofit.getPosts()
        }
    }
}