package xyz.kewiany.menusy.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OrderScreen(
    state: State<OrderViewModel.State>,
    eventHandler: (OrderViewModel.Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val results = state.value.results
        if (results.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f),
            ) {
                items(state.value.results) { item ->
                    Column(
                        modifier = Modifier
                            .background(Color.Yellow)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = item
                        )
                    }
                }
            }
        } else {
            Text(
                text = "No order"
            )
        }
    }
}