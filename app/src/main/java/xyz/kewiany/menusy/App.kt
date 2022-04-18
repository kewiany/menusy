package xyz.kewiany.menusy

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.api.MenuService
import xyz.kewiany.menusy.ui.BottomBar
import xyz.kewiany.menusy.ui.TopBar
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.ui.order.OrderViewModel
import xyz.kewiany.menusy.ui.search.SearchViewModel
import xyz.kewiany.menusy.ui.utils.NavGraph
import xyz.kewiany.menusy.ui.utils.Navigation
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel
import xyz.kewiany.menusy.usecase.GetMenusUseCaseImpl

@Composable
fun App() {
    val navController: NavHostController = rememberNavController()
    val welcomeViewModel = WelcomeViewModel()
    val menuService = MenuService()
    val getMenusUseCase = GetMenusUseCaseImpl(menuService)
    val menuEntryViewModel = MenuEntryViewModel(getMenusUseCase)
    val menuItemsViewModel = MenuItemsViewModel()
    val orderViewModel = OrderViewModel()
    val searchViewModel = SearchViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Navigation.Destination.WELCOME_PATH

    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    when (navBackStackEntry?.destination?.route) {
        Navigation.Destination.WELCOME_PATH -> {
            bottomBarState.value = false
        }
        else -> {
            bottomBarState.value = true
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                currentRoute = currentRoute
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                bottomBarState = bottomBarState
            )
        },
        content = {
            NavGraph(
                navController = navController,
                welcomeViewModel = welcomeViewModel,
                menuEntryViewModel = menuEntryViewModel,
                menuItemsViewModel = menuItemsViewModel,
                orderViewModel = orderViewModel,
                searchViewModel = searchViewModel
            )
        }
    )
}