package com.example.feedapp.feed.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feedapp.feed.ui.screen.FeedDetailsScreen
import com.example.feedapp.feed.ui.screen.FeedScreen
import com.example.feedapp.feed.viewModel.FeedViewModel


@Composable
fun FeedNavigationCompose() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = FeedScreenNavigationEnum.FEED_LIST.value) {
        composable(FeedScreenNavigationEnum.FEED_LIST.value) {
            FeedScreen(navController)
        }
        composable(FeedScreenNavigationEnum.FEED_DETAILS.value + "/{id}") { backNavigation ->
            val itemId = backNavigation.arguments?.getString("id")
            FeedDetailsScreen(navController, itemId)
        }
    }
}