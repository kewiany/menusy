package xyz.kewiany.menusy.ui.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
fun OrderScreen(
    state: State<OrderViewModel.State>,
    eventHandler: (OrderViewModel.Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Order"
        )
    }
}