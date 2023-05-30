package com.example.feedapp.feed.data.api

import com.example.feedapp.feed.data.api.model.FeedResponse
import retrofit2.Response
import retrofit2.http.GET

interface FeedApi {
 @GET("top-headlines")
 suspend fun getFeedResponse(): Response<FeedResponse>

}