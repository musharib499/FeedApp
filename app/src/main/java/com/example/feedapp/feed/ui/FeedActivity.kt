package com.example.feedapp.feed.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.feedapp.app.theme.FeedAppTheme
import com.example.feedapp.feed.ui.navigation.FeedNavigationCompose
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedAppTheme() {
                FeedNavigationCompose()
            }
        }
    }
}


