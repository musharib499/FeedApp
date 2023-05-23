package com.example.feedapp.feed.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.feedapp.app.theme.FeedAppTheme
import com.example.feedapp.feed.ui.navigation.FeedNavigationCompose
import com.example.feedapp.feed.ui.viewModel.FeedViewModel

class FeedActivity : ComponentActivity() {
    private val viewModel : FeedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedAppTheme() {
                FeedNavigationCompose(viewModel)
            }
        }
    }
}


