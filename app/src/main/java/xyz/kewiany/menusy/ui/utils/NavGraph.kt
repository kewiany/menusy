package xyz.kewiany.menusy.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.kewiany.menusy.ui.menu.MenuScreen
import xyz.kewiany.menusy.ui.menu.MenuViewModel
import xyz.kewiany.menusy.ui.order.OrderScreen
import xyz.kewiany.menusy.ui.order.OrderViewModel
import xyz.kewiany.menusy.ui.search.SearchScreen
import xyz.kewiany.menusy.ui.search.SearchViewModel
import xyz.kewiany.menusy.ui.utils.Navigation.Arg.VALUE_BOOLEAN
import xyz.kewiany.menusy.ui.welcome.WelcomeScreen
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel

object Navigation {

    object Destination {
        const val WELCOME_PATH = "welcome"
        const val MENU_PATH = "$WELCOME_PATH/{$VALUE_BOOLEAN}"
        const val ORDER_PATH = "order"
        const val SEARCH_PATH = "search"
    }

    object Arg {
        const val VALUE_BOOLEAN = "ValueBoolean"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Navigation.Destination.WELCOME_PATH,
    welcomeViewModel: WelcomeViewModel,
    menuViewModel: MenuViewModel,
    orderViewModel: OrderViewModel,
    searchViewModel: SearchViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Navigation.Destination.WELCOME_PATH
        ) {
            WelcomeDestination(
                navController = navController,
                viewModel = welcomeViewModel
            )
        }
        composable(
            route = Navigation.Destination.MENU_PATH,
            arguments = listOf(navArgument(VALUE_BOOLEAN) {
                type = NavType.StringType
            })
        ) {
            MenuDestination(
                value = it.arguments?.getString(VALUE_BOOLEAN) ?: "",
                viewModel = menuViewModel
            )
        }
        composable(
            route = Navigation.Destination.ORDER_PATH
        ) {
            OrderDestination(
                viewModel = orderViewModel
            )
        }
        composable(
            route = Navigation.Destination.SEARCH_PATH
        ) {
            SearchDestination(
                viewModel = searchViewModel
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
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler,
        onNavigationRequested = {
            navController.navigate("${Navigation.Destination.WELCOME_PATH}/$it")
        }
    )
}

@Composable
private fun MenuDestination(
    value: String,
    viewModel: MenuViewModel
) {
    MenuScreen(
        value = value,
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler
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

fun getTitleForRoute(route: String): String {
    return when (route) {
        Navigation.Destination.MENU_PATH -> "Menu"
        Navigation.Destination.WELCOME_PATH -> "Welcome"
        Navigation.Destination.ORDER_PATH -> "Order"
        Navigation.Destination.SEARCH_PATH -> "Search"
        else -> "Unknown route"
    }
}