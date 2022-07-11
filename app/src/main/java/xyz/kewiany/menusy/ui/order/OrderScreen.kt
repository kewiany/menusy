package xyz.kewiany.menusy.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.kewiany.menusy.ui.order.OrderViewModel.Event

@Composable
fun OrderScreen(
    state: State<OrderViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
//    val res by remember { mutableStateOf(state.value.results) }
    Column {
        Box(modifier = Modifier.weight(1f)) {
            val results = state.value.results
            if (results.isNotEmpty()) {
                LazyColumn {
                    items(results) { item ->
                        Column(
                            modifier = Modifier
                                .background(Color.Yellow)
                                .padding(10.dp)
                        ) {
                            Text(text = "${item.product.id} ${item.quantity}")
                        }
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