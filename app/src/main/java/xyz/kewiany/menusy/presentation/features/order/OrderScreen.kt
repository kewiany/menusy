package xyz.kewiany.menusy.presentation.features.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
        val data = state.value.results
        Box(modifier = Modifier.weight(1f)) {
            if (data.isNotEmpty()) {
                LazyColumn {
                    items(data) { item ->
                        val productId = item.product.id
                        OrderItem(
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
        if (data.isNotEmpty()) {
            Row {
                Text(
                    text = "Total (${state.value.totalQuantity.toString()})",
                    modifier = Modifier.weight(0.8f)
                )
                Text(
                    text = state.value.totalPrice.toString(),
                    modifier = Modifier.weight(0.2f)
                )
            }
            Button(onClick = { eventHandler(Event.PayButtonClicked) }) {
                Text("Pay")
            }
        }
    }
}