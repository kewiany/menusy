package xyz.kewiany.menusy.ui.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel

@Composable
fun MenuEntryScreen(
    state: State<MenuEntryViewModel.State>,
    eventHandler: (MenuEntryViewModel.Event) -> Unit,
    onNavigationRequested: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val item = "Food"
        Button(onClick = {
            onNavigationRequested(item)
        }) {
            Text(text = item)
        }
    }
}