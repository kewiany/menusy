package xyz.kewiany.menusy.presentation.features.menu.items

import xyz.kewiany.menusy.domain.model.Product

object ProductMapper {
    fun map(product: Product, quantity: Int = 0) = ProductUiItem(
        product.id,
        product.name,
        product.description,
        product.price.toString(),
        quantity
    )
}