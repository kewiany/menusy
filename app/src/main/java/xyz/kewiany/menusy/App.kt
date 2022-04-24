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
import xyz.kewiany.menusy.ui.MainViewModel
import xyz.kewiany.menusy.ui.TopBar
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.ui.order.OrderViewModel
import xyz.kewiany.menusy.ui.search.SearchViewModel
import xyz.kewiany.menusy.ui.utils.NavGraph
import xyz.kewiany.menusy.ui.utils.Navigator
import xyz.kewiany.menusy.ui.utils.Screen
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel
import xyz.kewiany.menusy.usecase.GetMenusUseCaseImpl

@Composable
fun App(navigator: Navigator) {
    val navController: NavHostController = rememberNavController()
    val mainViewModel = MainViewModel(navigator)
    val welcomeViewModel = WelcomeViewModel(navigator)
    val menuService = MenuService()
    val getMenusUseCase = GetMenusUseCaseImpl(menuService)
    val menuEntryViewModel = MenuEntryViewModel(getMenusUseCase)
    val menuItemsViewModel = MenuItemsViewModel()
    val orderViewModel = OrderViewModel()
    val searchViewModel = SearchViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    when (navBackStackEntry?.destination?.route) {
        Screen.WelcomeScreen.route -> {
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
                currentRoute = ""
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                bottomBarState = bottomBarState,
                eventHandler = mainViewModel.eventHandler
            )
        },
        content = {
            NavGraph(
                navController = navController,
                navigator = navigator,
                welcomeViewModel = welcomeViewModel,
                menuEntryViewModel = menuEntryViewModel,
                menuItemsViewModel = menuItemsViewModel,
                orderViewModel = orderViewModel,
                searchViewModel = searchViewModel
            )
        }
    )
}