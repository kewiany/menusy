package xyz.kewiany.menusy

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.ui.BottomBar
import xyz.kewiany.menusy.ui.menu.MenuViewModel
import xyz.kewiany.menusy.ui.utils.NavGraph
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel

@Composable
fun App() {
    val navController: NavHostController = rememberNavController()
    val welcomeViewModel = WelcomeViewModel()
    val menuViewModel = MenuViewModel()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        NavGraph(
            navController = navController,
            welcomeViewModel = welcomeViewModel,
            menuViewModel = menuViewModel
        )
    }
}