package xyz.kewiany.menusy.presentation.features.search

import xyz.kewiany.menusy.presentation.features.menu.items.ProductUiItem
import xyz.kewiany.menusy.presentation.utils.UiItem

object ProductUItemModifier {

    fun decreaseQuantity(items: List<UiItem>, productId: String): List<UiItem> {
        val index = items.indexOfFirst { it.id == productId }
        val product = items[index] as ProductUiItem
        val quantity = product.quantity - 1

        return changeQuantity(items, index, product, quantity)
    }

    fun increaseQuantity(items: List<UiItem>, productId: String): List<UiItem> {
        val index = items.indexOfFirst { it.id == productId }
        val product = items[index] as ProductUiItem
        val quantity = product.quantity + 1

        return changeQuantity(items, index, product, quantity)
    }

    private fun changeQuantity(
        items: List<UiItem>,
        index: Int,
        product: ProductUiItem,
        quantity: Int
    ): List<UiItem> {
        val newProduct = ProductUiItem(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            quantity = quantity
        )
        return items.toMutableList().apply {
            remove(product)
            add(index, newProduct)
        }
    }
}