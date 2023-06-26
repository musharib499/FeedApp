package com.example.feedapp.feed.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedapp.feed.data.api.model.ArticlesItem
import com.example.feedapp.feed.repository.FeedRepository
import com.example.feedapp.feed.userIntent.FeedIntent
import com.example.feedapp.feed.userIntent.FeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedDetailsViewModel @Inject constructor(private val repository: FeedRepository) : ViewModel() {

    private val _articlesItem = MutableStateFlow<ArticlesItem?>(null)
    val articlesItem = _articlesItem

    private val userIntent = Channel<FeedIntent>(Channel.UNLIMITED)
    var state = MutableStateFlow<FeedState>(FeedState.Idle)
        private set

    init {
         handleIntent()
    }

    fun sendEvent(intent: FeedIntent) = viewModelScope.launch {
        userIntent.send(intent)
    }

    private fun handleIntent() = viewModelScope.launch {
        userIntent.consumeAsFlow().collect { collector ->
            when (collector) {
                is FeedIntent.ArticlesId -> {
                    getFeedArticles(collector.articlesId)
                }

                is FeedIntent.LikeArticles -> toggleLike(collector.articles)
                else -> Unit
            }
        }
    }

    private fun getFeedArticles(id:String?) = viewModelScope.launch(Dispatchers.IO){
        if (id.isNullOrEmpty()) return@launch
        state.emit(FeedState.Loading)
        repository.getArticles(id.toInt()).collect {
            _articlesItem.emit(it)
            state.emit(FeedState.Articles(it))
        }
    }

    private fun toggleLike(articlesItem: ArticlesItem?) = viewModelScope.launch(Dispatchers.IO) {
        val isLike = articlesItem?.isLiked?.not()
        repository.toggleLikeUnLike(articlesItem?.articlesId ?: -1, isLike ?: false)
    }
}