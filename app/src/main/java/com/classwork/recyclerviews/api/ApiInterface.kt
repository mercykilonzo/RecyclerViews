package com.classwork.recyclerviews.model

import com.classwork.recyclerviews.model.Comment
import com.classwork.recyclerviews.model.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("/posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: Int): Response<Post>

    @GET("posts/{postId}/comments")
    fun getCommentsForPost(@Path("postId") postId: Int): Call<List<Comment>>


}