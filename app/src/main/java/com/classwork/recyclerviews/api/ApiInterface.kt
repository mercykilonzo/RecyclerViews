package com.classwork.recyclerviews.api

import com.classwork.recyclerviews.model.Post
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("/posts/{postId}")
    fun getPostById(@Path("postId")postId:Int):Call<Post>

    @GET("/posts/{postId}/comments")
    fun getCommentsByPostId(@Query("postId")postId: Int):Call<List<Comment>>
}