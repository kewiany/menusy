package xyz.kewiany.menusy.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.ui.welcome.WelcomeScreen
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel

object Navigation {

    object Destination {
        const val WELCOME_PATH = "welcome"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Navigation.Destination.WELCOME_PATH,
    welcomeViewModel: WelcomeViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Navigation.Destination.WELCOME_PATH) {
            WelcomeDestination(
                navController = navController,
                viewModel = welcomeViewModel
            )
        }
    }
}

@Composable
private fun WelcomeDestination(
    navController: NavHostController,
    viewModel: WelcomeViewModel
) {
    WelcomeScreen(
        viewModel.state.collectAsState(),
        viewModel.eventHandler
    )
}
