package xyz.kewiany.menusy.ui.menu.entry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel.Event.MenuClicked

@Composable
fun MenuEntryScreen(
    state: State<MenuEntryViewModel.State>,
    eventHandler: (MenuEntryViewModel.Event) -> Unit,
    onNavigationRequested: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val menus = state.value.menus
        Column {
            Text(text = menus.toString())

            val item = "Food"
            Button(onClick = {
                eventHandler(MenuClicked(menus[0].id))
                onNavigationRequested(item)
            }) {
                Text(text = item)
            }
        }
    }
}