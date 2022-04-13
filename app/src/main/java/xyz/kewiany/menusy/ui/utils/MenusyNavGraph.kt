package xyz.kewiany.menusy.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.ui.welcome.WelcomeScreen
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel


@Composable
fun MenusyNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MenusyNavigation.Route.WELCOME_ROUTE,
    welcomeViewModel: WelcomeViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = MenusyNavigation.Route.WELCOME_ROUTE
        ) {
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
        isLoading = viewModel.isLoading.collectAsState()
    )
}
