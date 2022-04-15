package xyz.kewiany.menusy.ui.menu.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
fun MenuItemsScreen(
    value: String,
    state: State<MenuItemsViewModel.State>,
    eventHandler: (MenuItemsViewModel.Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = value)
    }
}