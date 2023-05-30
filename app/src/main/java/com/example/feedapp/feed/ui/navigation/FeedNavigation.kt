package com.example.feedapp.feed.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedapp.feed.ui.screen.FeedDetailsScreen
import com.example.feedapp.feed.ui.screen.FeedScreen
import com.example.feedapp.feed.ui.viewModel.FeedViewModel


@Composable
fun FeedNavigationCompose(viewModel: FeedViewModel) {
    val navController = rememberNavController()
     viewModel.navController = navController
    NavHost(navController = navController, startDestination = FeedScreenNavigationEnum.FEED_LIST.value) {
        composable(FeedScreenNavigationEnum.FEED_LIST.value) {
            FeedScreen(viewModel)
        }
        composable(FeedScreenNavigationEnum.FEED_DETAILS.value) {
            FeedDetailsScreen(viewModel)
        }

        /*composable(FeedScreenNavigationEnum.FEED_DETAILS.value + "/id", arguments = listOf(navArgument("id") {
            type = NavType.IntType
        })) { backNavigation ->

            FeedDetailsScreen(id = backNavigation.arguments?.getInt("id") ?: 0, navController = navController)
        }*/
    }
}