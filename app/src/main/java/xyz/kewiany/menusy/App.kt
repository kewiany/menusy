package xyz.kewiany.menusy

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.kewiany.menusy.api.MenuService
import xyz.kewiany.menusy.navigation.NavGraph
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.BottomBar
import xyz.kewiany.menusy.ui.MainViewModel
import xyz.kewiany.menusy.ui.TopBar
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.ui.order.OrderViewModel
import xyz.kewiany.menusy.ui.search.SearchViewModel
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel
import xyz.kewiany.menusy.usecase.GetMenusUseCaseImpl

@Composable
fun App(
    navigator: Navigator
) {
    val navController = rememberNavController()
    val startDestination = NavigationDirections.welcome.destination
    val mainViewModel = MainViewModel(navigator)
    val welcomeViewModel = WelcomeViewModel(navigator)
    val menuService = MenuService()
    val getMenusUseCase = GetMenusUseCaseImpl(menuService)
    val menuEntryViewModel = MenuEntryViewModel(navigator, getMenusUseCase)
    val menuItemsViewModel = MenuItemsViewModel()
    val orderViewModel = OrderViewModel()
    val searchViewModel = SearchViewModel()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    LaunchedEffect(navigator.commands) {
        navigator.commands.onEach { command ->
            val destination = command.destination

            if (command == NavigationDirections.menuEntry || command == NavigationDirections.order) {
                navController.navigate(destination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } else {
                navController.navigate(destination)
            }

        }.launchIn(this)
    }

    bottomBarState.value = currentRoute != NavigationDirections.welcome.destination
            && currentRoute != NavigationDirections.search.destination

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                eventHandler = mainViewModel.eventHandler,
                currentRoute = currentRoute
            )
        },
        bottomBar = {
            BottomBar(
                bottomBarState = bottomBarState,
                eventHandler = mainViewModel.eventHandler
            )
        },
        content = {
            NavGraph(
                navController = navController,
                startDestination = startDestination,
                welcomeViewModel = welcomeViewModel,
                menuEntryViewModel = menuEntryViewModel,
                menuItemsViewModel = menuItemsViewModel,
                orderViewModel = orderViewModel,
                searchViewModel = searchViewModel
            )
        }
    )
}