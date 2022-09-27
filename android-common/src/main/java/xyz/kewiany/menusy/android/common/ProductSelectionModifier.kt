package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.UiItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductSelectionModifier @Inject constructor() : ProductModifier() {

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

}