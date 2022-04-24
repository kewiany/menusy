package xyz.kewiany.menusy.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.kewiany.menusy.ui.menu.MenuEntryScreen
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.ui.menu.items.MenuItemsScreen
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.ui.order.OrderScreen
import xyz.kewiany.menusy.ui.order.OrderViewModel
import xyz.kewiany.menusy.ui.search.SearchScreen
import xyz.kewiany.menusy.ui.search.SearchViewModel
import xyz.kewiany.menusy.ui.utils.Screen.*
import xyz.kewiany.menusy.ui.welcome.WelcomeScreen
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    navigator: Navigator,
    welcomeViewModel: WelcomeViewModel,
    menuEntryViewModel: MenuEntryViewModel,
    menuItemsViewModel: MenuItemsViewModel,
    orderViewModel: OrderViewModel,
    searchViewModel: SearchViewModel
) {
    LaunchedEffect("navigation") {
        navigator.destination.onEach { destination ->
            if (destination == MenuEntryScreen || destination == OrderScreen) {
                navController.navigate(destination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } else {
                navController.navigate(destination.route)
            }
        }.launchIn(this)
    }
    NavHost(
        navController = navController,
        startDestination = WelcomeScreen.route
    ) {
        composable(
            route = WelcomeScreen.route
        ) {
            WelcomeDestination(
                viewModel = welcomeViewModel
            )
        }
        composable(
            route = MenuEntryScreen.route
        ) {
            MenuEntryDestination(
                navController = navController,
                viewModel = menuEntryViewModel
            )
        }
        composable(
            route = MenuItemsScreen.route,
            arguments = listOf(navArgument(VALUE_BOOLEAN_ARG) {
                type = NavType.StringType
            })
        ) {
            MenuItemsDestination(
                value = it.arguments?.getString(VALUE_BOOLEAN_ARG) ?: "",
                viewModel = menuItemsViewModel
            )
        }
        composable(
            route = OrderScreen.route,
        ) {
            OrderDestination(
                viewModel = orderViewModel
            )
        }
        composable(
            route = SearchScreen.route,
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
    navController: NavHostController,
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