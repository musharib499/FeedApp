package com.example.feedapp.feed.repository

import com.example.feedapp.base.component.baseApi.BaseApiResponse
import com.example.feedapp.base.component.baseApi.BaseApiResult
import com.example.feedapp.feed.data.api.FeedApi
import com.example.feedapp.feed.data.api.model.ArticlesItem
import com.example.feedapp.feed.data.api.model.FeedResponse
import com.example.feedapp.feed.database.FeedDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedRepository @Inject constructor(private val api: FeedApi, private val feedDao: FeedDao, private val defaultDispatcher: CoroutineDispatcher) : BaseApiResponse() {
    suspend fun getFeedResponse(source: String): BaseApiResult<FeedResponse> {
        val response = withContext(defaultDispatcher) {
            safeApiCall {
                api.getFeedResponse(source).apply {
                    insertFeed(this.body())
                }
            }
        }
        return response
    }

    private fun insertFeed(feedResponse: FeedResponse?) {
        feedResponse?.articles?:return
        feedDao.insertFeedResponse(feedResponse.articles)
    }

    suspend fun getFeedListFeed(source: String): Flow<List<ArticlesItem>?> {
       return feedDao.getArticlesBySourceName(source)
    }

    suspend fun toggleLikeUnLike(id:Int,isLike:Boolean) {
        feedDao.toggleLikeUnLike(id, isLike)
    }

    suspend fun getArticles(id:Int):Flow<ArticlesItem?> {
       return feedDao.getArticles(id)
    }
}