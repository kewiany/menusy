package xyz.kewiany.menusy.ui.menu.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event

@Composable
fun MenuItemsScreen(
    menuId: String,
    state: State<MenuItemsViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "menu id $menuId")
            val products = state.value.products
            products.forEach { product ->
                Row(
                    modifier = Modifier.clickable {
                        eventHandler(Event.ProductClicked(product.id))
                    }
                ) {
                    Text(
                        text = product.id,
                        modifier = Modifier.weight(0.2f)
                    )
                    Text(
                        text = product.name,
                        modifier = Modifier.weight(0.8f)
                    )
                }
            }
        }
    }
}