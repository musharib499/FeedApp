package com.example.feedapp.feed.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.feedapp.base.component.ToolbarWidget
import com.example.feedapp.feed.data.api.model.ArticlesItem
import com.example.feedapp.feed.data.api.model.ClickTypeEnum
import com.example.feedapp.feed.showMessage
import com.example.feedapp.feed.userIntent.FeedIntent
import com.example.feedapp.feed.userIntent.FeedState
import com.example.feedapp.feed.viewModel.FeedDetailsViewModel


@Composable
fun FeedDetailsScreen(navController: NavController? = null,id: String? = "") {
    val feedViewModel = hiltViewModel<FeedDetailsViewModel>()
    feedViewModel.sendEvent(FeedIntent.ArticlesId(id))
    val context = LocalContext.current
    val state =  feedViewModel.state.collectAsState()
    when (state?.value) {
        is FeedState.Idle -> LoadingScreen()
        is FeedState.Loading -> LoadingScreen()
        is FeedState.Articles -> FeedDetail(navController, feedViewModel)
        is FeedState.Error -> {
            showMessage(context, "Hello")
        }
        else -> Unit
    }

}
@Composable
fun FeedDetail(navController: NavController?,viewModel: FeedDetailsViewModel) {
    val articlesItem = viewModel?.articlesItem?.collectAsState()
    val context = LocalContext.current
    ToolbarWidget(
        name = "FeedDetails",
        backClick = { navController?.navigateUp() },
        content = {
            articlesItem?.value?.let {
                FeedDetailItems(articlesItem = it) { type ->
                    when (type) {
                        ClickTypeEnum.LIKE -> viewModel.sendEvent(FeedIntent.LikeArticles(it))
                        else -> showMessage(context = context, message = type.name)
                    }
                }
            }
        })
}



@Composable
fun FeedDetailItems(articlesItem: ArticlesItem, onClickLike: (type: ClickTypeEnum) -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.White),
    ) {
        FeedImage(articlesItem.urlToImage)
        FeedContent(articlesItem) {
            onClickLike(it)
        }
    }
}

@Preview
@Composable
fun FeedDetailsScreenPreview() {
    FeedDetailsScreen()
}