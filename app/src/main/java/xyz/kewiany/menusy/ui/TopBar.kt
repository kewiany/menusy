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
import xyz.kewiany.menusy.ui.utils.Navigation.Destination.SEARCH_PATH
import xyz.kewiany.menusy.ui.utils.Navigation.Destination.WELCOME_PATH
import xyz.kewiany.menusy.ui.utils.getTitleForRoute

@Composable
fun TopBar(
    navController: NavHostController = rememberNavController(),
    currentRoute: String
) {
    TopAppBar(
        title = { Text(getTitleForRoute(currentRoute)) },
        navigationIcon = {
            val showBack = currentRoute == SEARCH_PATH
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
            val showSearch = currentRoute != SEARCH_PATH && currentRoute != WELCOME_PATH
            if (showSearch) {
                IconButton(onClick = {
                    navController.navigate(SEARCH_PATH)
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