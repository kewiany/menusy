package xyz.kewiany.menusy.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HistoryScreen(
    state: State<HistoryViewModel.State>,
    eventHandler: (HistoryViewModel.Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val items = state.value.items
        if (items.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f),
            ) {
                items(state.value.items) { item ->
                    with(item) {
                        HistoryItem(
                            name = productName,
                            description = productDescription,
                            price = productPrice,
                            quantity = quantity
                        )
                    }
                }
            }
        } else {
            Text(
                text = "No history"
            )
        }
    }
}

@Composable
private fun HistoryItem(
    name: String,
    description: String,
    price: String,
    quantity: String
) {
    Column(
        modifier = Modifier
            .background(Color.Magenta)
            .padding(10.dp)
    ) {
        Row {
            Text(
                text = name,
                modifier = Modifier.weight(0.8f)
            )
            Text(
                text = price,
                modifier = Modifier.weight(0.2f)
            )
        }
        Row {
            Text(
                text = quantity
            )
        }
        Row {
            Text(
                text = description
            )
        }
    }
}
