package xyz.kewiany.menusy.ui.menu.entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel.Event

@Composable
fun MenuEntryScreen(
    state: State<MenuEntryViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val menus = state.value.menus
        Column {
            menus.forEach { menu ->
                Row(
                    modifier = Modifier.clickable {
                        eventHandler(Event.MenuClicked(menu.id))
                    }
                ) {
                    Text(
                        text = menu.id,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = menu.name,
                        modifier = Modifier.weight(0.8f)
                    )
                }
            }
        }
    }
}