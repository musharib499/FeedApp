package com.example.feedapp.feed.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.feedapp.feed.model.ArticlesItem
import com.example.feedapp.feed.model.feedResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    var navController: NavController? = null
    private var _articlesList = MutableStateFlow(feedResponse.articles)
    var articlesList = _articlesList

    private val _articlesItem = MutableStateFlow(_articlesList.value?.get(0))
    val articlesItem = _articlesItem
    fun setFeedDetails(articlesItem: ArticlesItem?) {
        viewModelScope.launch {
            _articlesItem.emit(articlesItem)
        }
    }

    fun toggleLike(articlesItem: ArticlesItem?) {
        val isLike = articlesItem?.isLiked?.not()
        _articlesItem.update { it?.copy(isLiked = isLike) }
        _articlesList.update { currentList ->
            currentList?.map { listItem ->
                if (listItem?.publishedAt == articlesItem?.publishedAt) {
                    listItem?.copy(isLiked = isLike)
                } else {
                    listItem
                }
            }
        }

    }

}