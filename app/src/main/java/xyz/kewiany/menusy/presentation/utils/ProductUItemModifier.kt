package xyz.kewiany.menusy.presentation.utils

import xyz.kewiany.menusy.common.UiItem

object ProductUItemModifier {

    fun select(items: List<UiItem>, productId: String): Pair<Int, MutableList<UiItem>> {
        val (index, product) = findProductById(items, productId)
        val ordered = !product.ordered
        val quantity = if (ordered) 1 else 0

        val newProduct = ProductUiItem(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            ordered = ordered,
            quantity = quantity
        )
        val itemsWithSelectedProduct = items.toMutableList().apply {
            remove(product)
            add(index, newProduct)
        }
        return quantity to itemsWithSelectedProduct
    }

    fun decreaseQuantity(items: List<UiItem>, productId: String): Pair<Int, List<UiItem>> {
        val (index, product) = findProductById(items, productId)
        val quantity = product.quantity - 1

        val itemsWithUpdatedQuantity = changeQuantity(items, index, product, quantity)
        return quantity to itemsWithUpdatedQuantity
    }

    fun increaseQuantity(items: List<UiItem>, productId: String): Pair<Int, List<UiItem>> {
        val (index, product) = findProductById(items, productId)
        val quantity = product.quantity + 1

        val itemsWithUpdatedQuantity = changeQuantity(items, index, product, quantity)
        return quantity to itemsWithUpdatedQuantity
    }

    private fun findProductById(items: List<UiItem>, productId: String): Pair<Int, ProductUiItem> {
        val index = items.indexOfFirst { it.id == productId }
        return index to items[index] as ProductUiItem
    }

    private fun changeQuantity(
        items: List<UiItem>,
        index: Int,
        product: ProductUiItem,
        quantity: Int
    ): List<UiItem> {
        if (quantity < 0) throw ChangeQuantityException()
        val newProduct = ProductUiItem(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            ordered = if (quantity == 0) false else product.ordered,
            quantity = quantity
        )
        return items.toMutableList().apply {
            remove(product)
            add(index, newProduct)
        }
    }
}

class ChangeQuantityException : IllegalStateException()

