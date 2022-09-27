package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.UiItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductQuantityModifier @Inject constructor() : ProductModifier() {

    fun decreaseQuantity(items: List<UiItem>, productId: String): Pair<Int, List<UiItem>>? {
        val (index, product) = findProductById(items, productId)
        val quantity = product.quantity - 1

        val itemsWithUpdatedQuantity = changeQuantity(items, index, product, quantity) ?: return null
        return quantity to itemsWithUpdatedQuantity
    }

    fun increaseQuantity(items: List<UiItem>, productId: String): Pair<Int, List<UiItem>>? {
        val (index, product) = findProductById(items, productId)
        val quantity = product.quantity + 1

        val itemsWithUpdatedQuantity = changeQuantity(items, index, product, quantity) ?: return null
        return quantity to itemsWithUpdatedQuantity
    }

    private fun changeQuantity(
        items: List<UiItem>,
        index: Int,
        product: ProductUiItem,
        quantity: Int
    ): List<UiItem>? {
        if (quantity < 0) return null
        val newProduct = ProductUiItem(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            ordered = quantity != 0,
            quantity = quantity
        )
        return items.toMutableList().apply {
            remove(product)
            add(index, newProduct)
        }
    }
}