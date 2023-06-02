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
import com.example.feedapp.base.component.ToolbarWidget
import com.example.feedapp.feed.data.api.model.ArticlesItem
import com.example.feedapp.feed.data.api.model.ClickTypeEnum
import com.example.feedapp.feed.showMessage
import com.example.feedapp.feed.viewModel.FeedViewModel


@Composable
fun FeedDetailsScreen(feedViewModel: FeedViewModel? = null, id: Int = 0) {
    val articlesItem = feedViewModel?.articlesItem?.collectAsState()
    val context = LocalContext.current
    ToolbarWidget(
        name = "FeedDetails",
        backClick = { feedViewModel?.navController?.navigateUp() },
        content = {
            articlesItem?.value?.let {
                FeedDetails(articlesItem = it) { type ->
                    when (type) {
                        ClickTypeEnum.LIKE -> feedViewModel?.toggleLike(it)
                        else -> showMessage(context = context, message = type.name)
                    }
                }
            }
        })
}

@Composable
fun FeedDetails(articlesItem: ArticlesItem, onClickLike: (type: ClickTypeEnum) -> Unit) {
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