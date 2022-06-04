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
import xyz.kewiany.menusy.ui.MainViewModel.Event

@Composable
fun TopBar(
    navController: NavHostController = rememberNavController(),
    state: State<MainViewModel.State>,
    eventHandler: (Event) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
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
            val showSearchField = state.value.showSearchField
            if (showSearchField) {
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