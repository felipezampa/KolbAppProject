package com.example.kolbtestapp.service

import com.example.kolbtestapp.model.PostModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface ApiService {
    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<PostModel>

    @GET("posts")
    fun getPosts(): Call<List<PostModel>>
}

