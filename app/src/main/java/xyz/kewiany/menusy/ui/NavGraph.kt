package xyz.kewiany.menusy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryScreen
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.ui.menu.items.MenuItemsScreen
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.ui.order.OrderScreen
import xyz.kewiany.menusy.ui.order.OrderViewModel
import xyz.kewiany.menusy.ui.search.SearchScreen
import xyz.kewiany.menusy.ui.search.SearchViewModel
import xyz.kewiany.menusy.ui.welcome.WelcomeScreen
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = NavigationDirections.welcome.destination,
        ) {
            WelcomeDestination()
        }
        composable(
            route = NavigationDirections.menuEntry.destination
        ) {
            MenuEntryDestination()
        }
        composable(
            route = "menuEntry/{$MENU_ID}",
            arguments = NavigationDirections.menuItems().arguments
        ) {
            MenuItemsDestination(
                menuId = it.arguments?.getString(MENU_ID) ?: "",
            )
        }
        composable(
            route = NavigationDirections.order.destination
        ) {
            OrderDestination()
        }
        composable(
            route = NavigationDirections.search.destination,
        ) {
            SearchDestination()
        }
    }
}

@Composable
private fun WelcomeDestination() {
    val viewModel: WelcomeViewModel = hiltViewModel()
    WelcomeScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler
    )
}

@Composable
private fun MenuEntryDestination() {
    val viewModel: MenuEntryViewModel = hiltViewModel()
    MenuEntryScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler,
    )
}

@Composable
private fun MenuItemsDestination(menuId: String) {
    val viewModel: MenuItemsViewModel = hiltViewModel()
    MenuItemsScreen(
        menuId = menuId,
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler,
    )
}


@Composable
private fun OrderDestination() {
    val viewModel: OrderViewModel = hiltViewModel()
    OrderScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler
    )
}

@Composable
private fun SearchDestination() {
    val viewModel: SearchViewModel = hiltViewModel()
    SearchScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler
    )
}