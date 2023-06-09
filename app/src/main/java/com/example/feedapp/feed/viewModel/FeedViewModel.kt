package com.example.feedapp.feed.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.feedapp.base.component.baseApi.BaseApiResult
import com.example.feedapp.feed.data.api.model.ArticlesItem
import com.example.feedapp.feed.repository.FeedRepository
import com.example.feedapp.feed.userIntent.FeedIntent
import com.example.feedapp.feed.userIntent.FeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val repository: FeedRepository) : ViewModel() {
    private var _articlesList = MutableStateFlow<List<ArticlesItem>?>(null)
    var articlesList = _articlesList

    private val source: String = "TechCrunch"

    private val userIntent = Channel<FeedIntent>(Channel.UNLIMITED)
    var state = MutableStateFlow<FeedState>(FeedState.Idle)
        private set

    init {
        handleIntent()
        sendEvent(FeedIntent.FetchFeed)
    }

    fun sendEvent(intent: FeedIntent) = viewModelScope.launch {
        userIntent.send(intent)
    }

    private fun handleIntent() = viewModelScope.launch {
        userIntent.consumeAsFlow().collect { collector ->
            when (collector) {
                is FeedIntent.FetchFeed -> {
                    getFeedArticles(source)
                }

                is FeedIntent.LikeArticles -> toggleLike(collector.articles)
                else -> Unit
            }
        }
    }

    private fun getFeedArticles(source: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getFeedListFeed(source).collect { list ->
            if (list.isNullOrEmpty()) {
                getFeedResponse(source)
            }
            _articlesList.emit(list)
            state.emit(FeedState.ArticlesList(list))

        }
    }

    private fun getFeedResponse(source: String) = viewModelScope.launch {
        state.emit(FeedState.Loading)
        when (val result = repository.getFeedResponse(source)) {
            is BaseApiResult.Success -> {
                _articlesList.emit(result.data?.articles)
                state.emit(FeedState.ArticlesList(_articlesList.value.orEmpty()))
            }

            else -> state.emit(FeedState.Error(result.message))
        }
    }

    private fun toggleLike(articlesItem: ArticlesItem?) = viewModelScope.launch(Dispatchers.IO) {
        val isLike = articlesItem?.isLiked?.not()
        repository.toggleLikeUnLike(articlesItem?.articlesId ?: -1, isLike ?: false)
    }
}