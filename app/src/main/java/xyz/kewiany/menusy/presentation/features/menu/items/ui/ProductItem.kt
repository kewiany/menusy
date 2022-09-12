package xyz.kewiany.menusy.presentation.features.menu.items.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductItem(
    id: String,
    name: String,
    description: String,
    price: String,
    ordered: Boolean,
    quantity: Int,
    onProductClicked: (String) -> Unit,
    onDecreaseQuantityClicked: (String) -> Unit,
    onIncreaseQuantityClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Green)
            .padding(10.dp)
            .clickable { onProductClicked(id) }
    ) {
        Row {
            Text(
                text = id,
                modifier = Modifier.weight(0.2f)
            )
            Text(
                text = name,
                modifier = Modifier.weight(0.6f)
            )
            Text(
                text = price,
                modifier = Modifier.weight(0.2f)
            )
        }
        Row {
            Text(
                text = description
            )
        }
        if (ordered) {
            Row {
                Button(
                    modifier = Modifier.weight(0.2f),
                    onClick = {
                        onDecreaseQuantityClicked(id)
                    }
                ) {
                    Text(text = "-")
                }
                Text(
                    text = quantity.toString(),
                    modifier = Modifier.weight(0.6f),
                    textAlign = TextAlign.Center
                )
                Button(
                    modifier = Modifier.weight(0.2f),
                    onClick = {
                        onIncreaseQuantityClicked(id)
                    }
                ) {
                    Text(text = "+")
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    ProductItem(
        id = "id",
        name = "name",
        description = "description",
        price = "price",
        ordered = false,
        quantity = 0,
        onProductClicked = {

        },
        onIncreaseQuantityClicked = {

        },
        onDecreaseQuantityClicked = {

        }
    )
}