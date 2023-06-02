package com.example.feedapp.feed.data.api

import com.example.feedapp.feed.data.api.model.FeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {
    @GET("top-headlines")
    suspend fun getFeedResponse(@Query("sources") source: String): Response<FeedResponse>

}