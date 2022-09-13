package xyz.kewiany.menusy.presentation.features.menu.entry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.presentation.features.menu.entry.MenuEntryViewModel.Event
import xyz.kewiany.menusy.presentation.utils.ProgressBar

@Composable
fun MenuEntryScreen(
    state: State<MenuEntryViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    LaunchedEffect(Unit) {
        eventHandler(Event.TriggerLoadMenus)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = state.value.name.toString()
            )
            Text(
                text = state.value.address.toString()
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f)
            ) {
                items(state.value.menus) { item ->
                    MenuItem(
                        name = item.name,
                        onItemClicked = { eventHandler(Event.MenuClicked(item.id)) }
                    )
                }
            }
        }
        if (state.value.showLoading) {
            ProgressBar()
        }
    }
}