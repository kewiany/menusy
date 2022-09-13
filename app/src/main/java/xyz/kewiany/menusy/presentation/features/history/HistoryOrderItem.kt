package xyz.kewiany.menusy.presentation.features.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable fun HistoryOrderItem(
    date: String,
    totalPrice: String,
    totalQuantity: String,
) {
    Column(
        modifier = Modifier
            .background(Color.Magenta)
            .padding(10.dp)
    ) {
        Row {
            Text(
                text = date,
                modifier = Modifier.weight(0.8f)
            )
            Text(
                text = totalPrice,
                modifier = Modifier.weight(0.2f)
            )
            Text(
                text = totalQuantity,
                modifier = Modifier.weight(0.2f)
            )
        }
    }
}