package xyz.kewiany.menusy.ui

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.ui.utils.Navigation

@Composable
fun TopBar(
    navController: NavHostController = rememberNavController(),
    currentRoute: String
) {
    TopAppBar(
        title = { Text(getTitleForRoute(currentRoute)) }
    )
}

fun getTitleForRoute(route: String): String {
    return when (route) {
        Navigation.Destination.MENU_PATH -> "Menu"
        Navigation.Destination.WELCOME_PATH -> "Welcome"
        Navigation.Destination.ORDER_PATH -> "Order"
        else -> "Unknown route"
    }
}