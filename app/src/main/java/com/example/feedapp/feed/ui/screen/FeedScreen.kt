package com.example.feedapp.feed.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.feedapp.base.component.ToolbarWidget
import com.example.feedapp.feed.data.api.model.ArticlesItem
import com.example.feedapp.feed.data.api.model.ClickTypeEnum
import com.example.feedapp.feed.showMessage
import com.example.feedapp.feed.ui.navigation.FeedScreenNavigationEnum
import com.example.feedapp.feed.viewModel.FeedViewModel

@Composable
fun FeedScreen(feedViewModel: FeedViewModel? = null) {
    val feedList = feedViewModel?.articlesList?.collectAsState()
    val context = LocalContext.current
    ToolbarWidget(
        name = "FeedList",
        backClick = {},
        icon = Icons.Default.Home,
        content = {
            LazyColumn {

                items(feedList?.value.orEmpty()) { articlesItem ->
                    FeedCard(articlesItem, onClickLike = {
                        when (it) {
                            ClickTypeEnum.LIKE -> feedViewModel?.toggleLike(articlesItem)
                            else -> showMessage(context = context, message = it.name)
                        }
                    }) {
                        feedViewModel?.setFeedDetails(articlesItem)
                        feedViewModel?.navController?.navigate(FeedScreenNavigationEnum.FEED_DETAILS.value)

                    }
                }
            }
        })
}

@Composable
fun FeedCard(articlesItem: ArticlesItem?, onClickLike: (type: ClickTypeEnum) -> Unit, onClickActionNav: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { onClickActionNav() },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {


        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
        ) {
            FeedImage(articlesItem?.urlToImage)
            FeedContent(articlesItem) {
                onClickLike(it)
            }
            // ProfileContent(userProfile, Alignment.Start)
        }
    }
}

@Composable
fun FeedImage(image: String?) {
    val imageLoad = ImageRequest.Builder(LocalContext.current)
        .data(image)
        .crossfade(true)
        .build()
    AsyncImage(
        model = imageLoad,
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.FillWidth
    )

}

@Composable
fun FeedContent(articlesItem: ArticlesItem?, onClickLike: (type: ClickTypeEnum) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = articlesItem?.title.orEmpty(),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 10.dp),
            color = Color.Black
        )
        Text(
            text = articlesItem?.description.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 10.dp),
            color = Color.Gray
        )
        Text(
            text = articlesItem?.content.orEmpty(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 10.dp),
            color = Color.LightGray

        )

        FeedContentLikeUnlike(articlesItem) {
            onClickLike(it)
        }

    }

}

@Composable
fun FeedContentLikeUnlike(articlesItem: ArticlesItem?, onClickLike: (type: ClickTypeEnum) -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LikeButton(articlesItem) {
            onClickLike(ClickTypeEnum.LIKE)
        }
        ShareButton("Share") {
            onClickLike(ClickTypeEnum.SHARE)
        }
        ShareButton("Comment") {
            onClickLike(ClickTypeEnum.COMMENT)
        }
    }
}

@Composable
fun LikeButton(articlesItem: ArticlesItem?, onClickLike: () -> Unit) {
    Button(
        onClick = { onClickLike() }
    ) {
        Text(text = if (articlesItem?.isLiked == true) "Unlike" else "Like")
    }
}

@Composable
fun ShareButton(text: String, onClickLike: () -> Unit) {
    Button(
        onClick = { onClickLike() }
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun FeedCardPreview() {
    FeedScreen()
}