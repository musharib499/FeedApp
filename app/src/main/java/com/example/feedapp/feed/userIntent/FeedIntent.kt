package com.example.feedapp.feed.userIntent

import com.example.feedapp.feed.data.api.model.ArticlesItem

sealed class FeedIntent {
    object FetchFeed : FeedIntent()
    data class LikeArticles(val articles: ArticlesItem) : FeedIntent()
}