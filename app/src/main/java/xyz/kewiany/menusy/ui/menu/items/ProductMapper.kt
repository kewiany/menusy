package xyz.kewiany.menusy.ui.menu.items

import xyz.kewiany.menusy.entity.Product

object ProductMapper {
    fun map(product: Product) = ProductUiItem(
        product.id,
        product.name,
        product.description,
        product.price,
    )
}