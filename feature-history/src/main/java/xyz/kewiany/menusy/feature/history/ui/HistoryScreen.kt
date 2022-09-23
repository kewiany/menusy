package xyz.kewiany.menusy.feature.history.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.feature.history.*
import xyz.kewiany.menusy.feature.history.HistoryViewModel.Event

@Composable
fun HistoryScreen(
    state: State<HistoryViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    LaunchedEffect(Unit) {
        eventHandler(Event.TriggerLoadHistory)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val data = state.value.items
        if (data.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f),
            ) {
                items(data) { item ->
                    when (item) {
                        is HistoryOrderUiItem -> {
                            HistoryOrderItem(
                                date = item.date,
                                totalPrice = item.totalPrice,
                                totalQuantity = item.totalQuantity,
                                placeName = item.placeName,
                                placeAddress = item.placeAddress
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