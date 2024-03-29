package xyz.kewiany.menusy

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import dagger.hilt.android.EntryPointAccessors
import xyz.kewiany.menusy.android.common.navigation.MENU_ID
import xyz.kewiany.menusy.android.common.navigation.NavigationDirections
import xyz.kewiany.menusy.feature.history.HistoryViewModel
import xyz.kewiany.menusy.feature.history.ui.HistoryScreen
import xyz.kewiany.menusy.feature.language.ChangeLanguageViewModel
import xyz.kewiany.menusy.feature.language.ui.ChangeLanguageScreen
import xyz.kewiany.menusy.feature.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.feature.menu.entry.ui.MenuEntryScreen
import xyz.kewiany.menusy.feature.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.feature.menu.items.ui.MenuItemsScreen
import xyz.kewiany.menusy.feature.order.OrderScreen
import xyz.kewiany.menusy.feature.order.OrderViewModel
import xyz.kewiany.menusy.feature.search.SearchViewModel
import xyz.kewiany.menusy.feature.search.ui.SearchScreen
import xyz.kewiany.menusy.feature.welcome.WelcomeViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
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
            route = NavigationDirections.history.destination
        ) {
            HistoryDestination()
        }
        composable(
            route = NavigationDirections.search.destination,
        ) {
            SearchDestination()
        }
        dialog(
            route = NavigationDirections.changeLanguage.destination
        ) {
            ChangeLanguageDestination()
        }
    }
}

@Composable
private fun WelcomeDestination() {
    val viewModel: WelcomeViewModel = hiltViewModel()
    xyz.kewiany.menusy.feature.welcome.WelcomeScreen(
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
private fun MenuItemsDestination(
    menuId: String,
) {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).menuItemsViewModelFactory()

    val viewModel = viewModel<MenuItemsViewModel>(
        factory = MenuItemsViewModel.provideFactory(
            factory,
            menuId
        )
    )
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
private fun HistoryDestination() {
    val viewModel: HistoryViewModel = hiltViewModel()
    HistoryScreen(
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

@Composable
private fun ChangeLanguageDestination() {
    val viewModel: ChangeLanguageViewModel = hiltViewModel()
    ChangeLanguageScreen(
        state = viewModel.state.collectAsState(),
        eventHandler = viewModel.eventHandler
    )
}