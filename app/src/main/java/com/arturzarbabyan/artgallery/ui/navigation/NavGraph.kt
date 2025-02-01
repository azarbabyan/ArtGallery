package com.arturzarbabyan.artgallery.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import com.arturzarbabyan.artgallery.ui.screens.ArtScreen
import com.arturzarbabyan.artgallery.ui.screens.DetailScreen
import com.arturzarbabyan.artgallery.ui.viewmodel.ArtViewModel


sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Detail : Screen("detail/{artId}") {
        fun createRoute(artId: Int) = "detail/$artId"
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            val viewModel: ArtViewModel = hiltViewModel()
            ArtScreen(viewModel, navController)
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val artId = backStackEntry.arguments?.getString("artId")?.toIntOrNull()
            artId?.let {
                DetailScreen(artId) { navController.popBackStack() }
            }
        }
    }
}