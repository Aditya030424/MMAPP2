package com.example.mmapp.Networking2

import retrofit2.Response
import javax.inject.Inject

class PostRepository@Inject constructor(private val postService: PostService) {
    suspend fun createPost(postRequest: PostRequest): Response<PostResponse> {
        return postService.api.createPost(postRequest)
    }
    suspend fun getPosts(): Response<List<PostResponse>> {
        return postService.api.getPosts()
    }
}

