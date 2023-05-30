package com.example.feedapp.feed.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feedapp.app.theme.FeedAppTheme
import com.example.feedapp.feed.ui.navigation.FeedNavigationCompose
import com.example.feedapp.feed.ui.viewModel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedAppTheme() {
                val viewModel : FeedViewModel = hiltViewModel()
                FeedNavigationCompose(viewModel)
            }
        }
    }
}


