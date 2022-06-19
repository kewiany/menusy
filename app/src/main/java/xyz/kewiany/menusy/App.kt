package xyz.kewiany.menusy

import android.app.Application
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.BottomBar
import xyz.kewiany.menusy.ui.MainViewModel
import xyz.kewiany.menusy.ui.NavGraph
import xyz.kewiany.menusy.ui.TopBar

@HiltAndroidApp
class App : Application()

@Composable
fun App(
    navigator: Navigator,
) {
    val navController = rememberNavController()
    val startDestination = NavigationDirections.welcome.destination
    val mainViewModel: MainViewModel = hiltViewModel()
    val state = mainViewModel.state.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    mainViewModel.eventHandler(MainViewModel.Event.SetCurrentRoute(navBackStackEntry?.destination?.route))

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

    LaunchedEffect(navigator.back) {
        navigator.back.onEach {
            navController.popBackStack()
        }.launchIn(this)
    }

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                state = mainViewModel.state.collectAsState(),
                eventHandler = mainViewModel.eventHandler,
            )
        },
        bottomBar = {
            BottomBar(
                state,
                eventHandler = mainViewModel.eventHandler
            )
        },
        content = {
            NavGraph(
                navController = navController,
                startDestination = startDestination,
            )
        }
    )
}

object PreferencesKeys {
    val LANGUAGE = stringPreferencesKey("language")
}