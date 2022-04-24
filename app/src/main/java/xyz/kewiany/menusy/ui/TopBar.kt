package xyz.kewiany.menusy.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.ui.utils.Screen.SearchScreen
import xyz.kewiany.menusy.ui.utils.Screen.WelcomeScreen

@Composable
fun TopBar(
    navController: NavHostController = rememberNavController(),
    currentRoute: String
) {
    TopAppBar(
        title = { Text(currentRoute) },
        navigationIcon = {
            val showBack = currentRoute == SearchScreen.route
            if (showBack) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            val showSearch = currentRoute != SearchScreen.route && currentRoute != WelcomeScreen.route
            if (showSearch) {
                IconButton(onClick = {
                    navController.navigate(SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
        }
    )
}