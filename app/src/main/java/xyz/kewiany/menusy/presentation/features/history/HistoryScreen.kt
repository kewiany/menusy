package xyz.kewiany.menusy.presentation.features.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.presentation.features.history.HistoryViewModel.Event
import xyz.kewiany.menusy.presentation.utils.HistoryOrderUiItem
import xyz.kewiany.menusy.presentation.utils.HistoryProductUiItem

@Composable
fun HistoryScreen(
    state: State<HistoryViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    LaunchedEffect(Unit) {
        eventHandler(Event.TriggerLoadHistory)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val items = state.value.items
        if (items.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f),
            ) {
                items(state.value.items) { item ->
                    when (item) {
                        is HistoryOrderUiItem -> {
                            HistoryOrderItem(
                                date = item.date,
                                totalPrice = item.totalPrice,
                                totalQuantity = item.totalQuantity
                            )
                        }
                        is HistoryProductUiItem -> {
                            HistoryProductItem(
                                name = item.name,
                                description = item.description,
                                price = item.price,
                                quantity = item.quantity
                            )
                        }
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