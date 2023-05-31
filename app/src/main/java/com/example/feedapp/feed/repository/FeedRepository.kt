package com.example.feedapp.feed.repository

import com.example.feedapp.base.component.baseApi.BaseApiResponse
import com.example.feedapp.base.component.baseApi.BaseApiResult
import com.example.feedapp.feed.data.api.FeedApi
import com.example.feedapp.feed.data.api.model.FeedResponse
import com.example.feedapp.feed.data.api.model.Source
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedRepository @Inject constructor(private val api: FeedApi, private val defaultDispatcher: CoroutineDispatcher) : BaseApiResponse() {
    suspend fun getFeedResponse(source: String): BaseApiResult<FeedResponse> {
        val response = withContext(defaultDispatcher) {
            safeApiCall {
                api.getFeedResponse(source)
            }
        }
        return response
    }
}