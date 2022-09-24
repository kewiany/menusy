package xyz.kewiany.menusy.feature.menu.items.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductItem(
    name: String,
    description: String,
    price: String,
    ordered: Boolean,
    quantity: Int,
    onItemClicked: () -> Unit,
    onDecreaseQuantityClicked: () -> Unit,
    onIncreaseQuantityClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onItemClicked)
    ) {
        Column(
            modifier = Modifier
                .background(if (ordered) Color.LightGray else Color.White)
                .padding(10.dp)
        ) {
            Row {
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
                    text = description,
                    modifier = Modifier.weight(0.8f)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    if (ordered) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Ordered",
                            tint = Color.White
                        )
                    }
                }
            }
            if (ordered) {
                Row {
                    Button(
                        modifier = Modifier.weight(0.2f),
                        onClick = onDecreaseQuantityClicked
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
                        onClick = onIncreaseQuantityClicked
                    ) {
                        Text(text = "+")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    ProductItem(
        name = "name",
        description = "description",
        price = "price",
        ordered = false,
        quantity = 0,
        onItemClicked = {

        },
        onIncreaseQuantityClicked = {

        },
        onDecreaseQuantityClicked = {

        }
    )
}