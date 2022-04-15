package xyz.kewiany.menusy.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
fun SearchScreen(
    state: State<SearchViewModel.State>,
    eventHandler: (SearchViewModel.Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Search"
        )
    }
}