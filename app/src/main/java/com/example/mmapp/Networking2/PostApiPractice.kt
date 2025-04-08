package com.example.mmapp.Networking2

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostApiPractice {
    @POST("/api/users")
    suspend fun createPost(@Body postRequest: PostRequest): Response<PostResponse>
    @GET("/api/users")
    suspend fun getPosts(): Response<List<PostResponse>>

}