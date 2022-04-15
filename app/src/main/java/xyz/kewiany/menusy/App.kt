package xyz.kewiany.menusy

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.ui.BottomBar
import xyz.kewiany.menusy.ui.menu.MenuViewModel
import xyz.kewiany.menusy.ui.utils.NavGraph
import xyz.kewiany.menusy.ui.utils.Navigation
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel

@Composable
fun App() {
    val navController: NavHostController = rememberNavController()
    val welcomeViewModel = WelcomeViewModel()
    val menuViewModel = MenuViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Navigation.Destination.WELCOME_PATH
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(getTitleForRoute(currentRoute)) }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) {
        NavGraph(
            navController = navController,
            welcomeViewModel = welcomeViewModel,
            menuViewModel = menuViewModel
        )
    }
}

fun getTitleForRoute(route: String): String {
    return when (route) {
        Navigation.Destination.MENU_PATH -> "Menu"
        Navigation.Destination.WELCOME_PATH -> "Welcome"
        else -> "Unknown route"
    }
}