package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.model.Menu
import xyz.kewiany.menusy.model.OrderedProduct
import xyz.kewiany.menusy.model.Product

fun obtainMenuContentUIItems(
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<UiItem> {
    val items = mutableListOf<UiItem>()

    val matchedProducts = matchProductsWithOrderedProducts(products, orderedProducts)
    items.addAll(matchedProducts)
    return items
}

fun obtainMenuContentUIItems(
    menu: Menu,
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<UiItem> {
    val items = mutableListOf<UiItem>()

    val groupedProductsByCategory = products.groupBy { it.categoryId }.entries
    groupedProductsByCategory.forEach { (categoryId, products) ->
        val category = menu.categories?.find { it.id == categoryId }
        category?.asUIItem()?.also(items::add)

        val matchedProducts = matchProductsWithOrderedProducts(products, orderedProducts)
        items.addAll(matchedProducts)
    }
    return items
}

private fun matchProductsWithOrderedProducts(
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<ProductUiItem> {
    return products.map { product ->
        val matchedProductWithOrderedProduct = orderedProducts
            .firstOrNull { orderedProduct -> orderedProduct.product.id == product.id }

        if (matchedProductWithOrderedProduct != null) {
            val orderedQuantity = matchedProductWithOrderedProduct.quantity
            matchedProductWithOrderedProduct.product
            product.asUIItem(orderedQuantity)
        } else {
            product.asUIItem()
        }
    }
}