package xyz.kewiany.menusy.presentation.utils

import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.model.asUIItem

interface UiItem {
    val id: String
}

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
        val matchedProductWithOrderedProduct = orderedProducts.firstOrNull { orderedProduct ->
            orderedProduct.product.id == product.id
        }
        if (matchedProductWithOrderedProduct != null) {
            val orderedQuantity = matchedProductWithOrderedProduct.quantity
            product.asUIItem(orderedQuantity)
        } else {
            product.asUIItem()
        }
    }
}