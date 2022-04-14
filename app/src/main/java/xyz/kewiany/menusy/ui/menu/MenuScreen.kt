package xyz.kewiany.menusy.ui.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
fun MenuScreen(
    value: String,
    state: State<MenuViewModel.State>,
    eventHandler: (MenuViewModel.Event) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = value
        )
    }
}