package xyz.kewiany.menusy.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.ui.MainViewModel.Event

@Composable
fun TopBar(
    navController: NavHostController = rememberNavController(),
    eventHandler: (Event) -> Unit,
    currentRoute: String
) {
    TopAppBar(
        title = { Text(currentRoute) },
        navigationIcon = {
            val showBack = currentRoute == NavigationDirections.search.destination
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
            val showSearch = currentRoute != NavigationDirections.search.destination
                    && currentRoute != NavigationDirections.welcome.destination
            if (showSearch) {
                IconButton(onClick = {
                    eventHandler(Event.SearchClicked)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
            IconButton(onClick = {
                eventHandler(Event.ChangeLanguageClicked)
            }) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Change language",
                    tint = Color.White
                )
            }
        }
    )
}