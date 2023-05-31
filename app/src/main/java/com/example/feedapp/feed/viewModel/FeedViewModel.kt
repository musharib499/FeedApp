package com.example.feedapp.feed.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.feedapp.base.component.baseApi.BaseApiResult
import com.example.feedapp.feed.data.api.model.ArticlesItem
import com.example.feedapp.feed.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val repository: FeedRepository) : ViewModel() {
    var navController: NavController? = null
    private var _articlesList = MutableStateFlow<List<ArticlesItem>?>(null)
    var articlesList = _articlesList

    private val _articlesItem = MutableStateFlow<ArticlesItem?>(_articlesList.value?.get(0))
    val articlesItem = _articlesItem

    private val source: String = "techcrunch"
    fun setFeedDetails(articlesItem: ArticlesItem?) {
        viewModelScope.launch {
            _articlesItem.emit(articlesItem)
        }
    }

    init {
        getFeedResponse(source)
    }

    private fun getFeedResponse(source: String) = viewModelScope.launch {
        when (val result = repository.getFeedResponse(source)) {
            is BaseApiResult.Success -> _articlesList.emit(result.data?.articles)
            else -> null
        }
    }

    fun toggleLike(articlesItem: ArticlesItem?) {
        val isLike = articlesItem?.isLiked?.not()
        _articlesItem.update { it?.copy(isLiked = isLike) }
        _articlesList.update { currentList ->
            currentList?.map { listItem ->
                if (listItem.publishedAt == articlesItem?.publishedAt) {
                    listItem.copy(isLiked = isLike)
                } else {
                    listItem
                }
            }
        }

    }


}