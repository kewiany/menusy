package xyz.kewiany.menusy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    welcomeViewModel: WelcomeViewModel,
    menuEntryViewModel: MenuEntryViewModel,
    menuItemsViewModel: MenuItemsViewModel,
    orderViewModel: OrderViewModel,
    searchViewModel: SearchViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = NavigationDirections.welcome.destination,
        ) {
            WelcomeDestination(
                viewModel = welcomeViewModel
            )
        }
        composable(
            route = NavigationDirections.menuEntry.destination
        ) {
            MenuEntryDestination(
                viewModel = menuEntryViewModel
            )
        }
        composable(
            route = "menuEntry/{$MENU_ID}",
            arguments = NavigationDirections.menuItems().arguments
        ) {
            MenuItemsDestination(
                value = it.arguments?.getString(MENU_ID) ?: "",
                viewModel = menuItemsViewModel
            )
        }
        composable(
            route = NavigationDirections.order.destination
        ) {
            OrderDestination(
                viewModel = orderViewModel
            )
        }
        composable(
            route = NavigationDirections.search.destination,
        ) {
            SearchDestination(
                viewModel = searchViewModel
            )
        }
    }
}

@Composable
private fun WelcomeDestination(
    viewModel: WelcomeViewModel
) {
    WelcomeScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler
    )
}

@Composable
private fun MenuEntryDestination(
    viewModel: MenuEntryViewModel
) {
    MenuEntryScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler,
        onNavigationRequested = {
//            navController.navigate("${MenuEntryScreen.route}/$it")
        }
    )
}

@Composable
private fun MenuItemsDestination(
    value: String,
    viewModel: MenuItemsViewModel
) {
    MenuItemsScreen(
        value = value,
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler,
    )
}


@Composable
private fun OrderDestination(
    viewModel: OrderViewModel
) {
    OrderScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler
    )
}

@Composable
private fun SearchDestination(
    viewModel: SearchViewModel
) {
    SearchScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler
    )
}