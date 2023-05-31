package com.example.feedapp.feed.userIntent

import com.example.feedapp.feed.data.api.model.ArticlesItem

sealed class FeedState {
    object Idle : FeedState()
    object Loading : FeedState()
    data class ArticlesList(val articlesList: List<ArticlesItem>?) : FeedState()
    data class Error(val error: String?) : FeedState()
}