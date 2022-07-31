package xyz.kewiany.menusy.presentation.features.menu.entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(1f)
        ) {
            items(state.value.menus) { item ->
                Row(
                    modifier = Modifier.clickable {
                        eventHandler(Event.MenuClicked(item.id))
                    }
                ) {
                    Text(
                        text = item.id,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = item.name,
                        modifier = Modifier.weight(0.8f)
                    )
                }
            }
        }
        if (state.value.showLoading) {
            ProgressBar()
        }
    }
}