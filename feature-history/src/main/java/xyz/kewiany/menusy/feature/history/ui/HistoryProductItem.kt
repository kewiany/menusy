package xyz.kewiany.menusy.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable fun HistoryProductItem(
    name: String,
    description: String,
    price: String,
    quantity: String
) {
    Column(
        modifier = Modifier
            .background(Color.Yellow)
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