package xyz.kewiany.menusy.presentation.features.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OrderItem(
    name: String,
    price: String,
    quantity: Int,
    onDeleteClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Row {
            Text(
                text = quantity.toString(),
                modifier = Modifier.weight(0.2f)
            )
            Text(
                text = name,
                modifier = Modifier.weight(0.4f)
            )
            Text(
                text = price,
                modifier = Modifier.weight(0.2f)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Black,
                modifier = Modifier
                    .clickable(onClick = onDeleteClicked)
            )
        }
    }
}

@Preview
@Composable
fun OrderItemPreview() {
    OrderItem(
        name = "name",
        price = "price",
        quantity = 0,
    ) {

    }
}