package xyz.kewiany.menusy.presentation.features.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.presentation.features.order.OrderViewModel.Event

@Composable
fun OrderScreen(
    state: State<OrderViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    LaunchedEffect(Unit) {
        eventHandler(Event.TriggerLoadOrder)
    }
    Column {
        Box(modifier = Modifier.weight(1f)) {
            val items = state.value.results
            if (items.isNotEmpty()) {
                LazyColumn {
                    items(items) { item ->
                        val productId = item.product.id
                        OrderItem(
                            productId = productId,
                            name = item.product.name,
                            price = item.product.price.toString(),
                            quantity = item.quantity,
                            onDeleteClicked = {
                                eventHandler(Event.DeleteProductClicked(productId))
                            }
                        )
                    }
                }
            } else {
                Text(text = "No order")
            }
        }
        Button(onClick = { eventHandler(Event.PayButtonClicked) }) {
            Text("Pay")
        }
    }
}