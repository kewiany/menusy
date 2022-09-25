package xyz.kewiany.menusy.presentation.utils

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.MainViewModel
import xyz.kewiany.menusy.MainViewModel.Event
import xyz.kewiany.menusy.feature.search.ui.SearchBar

@Composable
fun TopBar(
    navController: NavHostController = rememberNavController(),
    state: State<MainViewModel.State>,
    eventHandler: (Event) -> Unit
) {
    val currentRoute = state.value.currentRoute ?: ""

    TopAppBar(
        title = { Text(currentRoute) },
        navigationIcon = {
            val showBackButton = state.value.showBackButton
            if (showBackButton) {
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
            val showSearchField = state.value.showSearchBar
            if (showSearchField) {
                SearchBar(
                    searchText = state.value.searchText,
                    showClearButton = state.value.showClearButton,
                    onSearchTextChanged = { text ->
                        eventHandler(Event.SearchTextChanged(text))
                    },
                    onSearchFocused = { isFocused ->
                        eventHandler(Event.SearchFocused(isFocused))
                    },
                    onSearchClearButtonClicked = {
                        eventHandler(Event.ClearSearchClicked)
                    }
                )
            }
            val showSearchButton = state.value.showSearchButton
            if (showSearchButton) {
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
                eventHandler(Event.HistoryClicked)
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "History",
                    tint = Color.White
                )
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