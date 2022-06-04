package xyz.kewiany.menusy.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.ui.MainViewModel.Event

@Composable
fun TopBar(
    navController: NavHostController = rememberNavController(),
    state: State<MainViewModel.State>,
    eventHandler: (Event) -> Unit,
    currentRoute: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    TopAppBar(
        title = { Text(currentRoute) },
        navigationIcon = {
            val showBack = currentRoute == NavigationDirections.search.destination
            if (showBack) {
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
            val isSearchScreen = currentRoute == NavigationDirections.search.destination
            if (isSearchScreen) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .onFocusChanged { focusState ->
                            val isFocused = focusState.isFocused
                            eventHandler(Event.SearchFocused(isFocused))
                        }
                        .focusRequester(focusRequester),
                    value = state.value.searchText,
                    onValueChange = { text ->
                        eventHandler(Event.SearchTextChanged(text))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    ),
                    trailingIcon = {
                        val showClearButton = state.value.showClearButton
                        if (showClearButton) {
                            IconButton(onClick = {
                                eventHandler(Event.ClearSearchClicked)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                )
            }
            val showSearch = currentRoute != NavigationDirections.search.destination
                    && currentRoute != NavigationDirections.welcome.destination
            if (showSearch) {
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