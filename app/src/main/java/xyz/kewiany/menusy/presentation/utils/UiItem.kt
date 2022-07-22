package xyz.kewiany.menusy.presentation.utils

import xyz.kewiany.menusy.domain.model.Category
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.model.asUIItem

interface UiItem {
    val id: String
}

fun obtainUiItems(
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<UiItem> {
    val items = mutableListOf<UiItem>()
    val matchedProducts = matchProductsWithOrderedProducts(products, orderedProducts)
    items.addAll(matchedProducts)
    return items
}

fun obtainUiItems(
    categories: List<Category>?,
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<UiItem> {
    val items = mutableListOf<UiItem>()
    val groupedProducts = products.groupBy { it.categoryId }.entries

    groupedProducts.forEach { (categoryId, products) ->
        val category = categories?.find { it.id == categoryId }
        if (category != null) items.add(category.asUIItem())

        val matchedProducts = matchProductsWithOrderedProducts(products, orderedProducts)
        items.addAll(matchedProducts)
    }
    return items
}

private fun matchProductsWithOrderedProducts(
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<ProductUiItem> = products.map { product ->
    val matchedProductWithOrderedProduct = orderedProducts
        .firstOrNull { orderedProduct -> orderedProduct.product.id == product.id }

    if (matchedProductWithOrderedProduct != null) {
        val orderedQuantity = matchedProductWithOrderedProduct.quantity
        product.asUIItem(orderedQuantity)
    } else {
        product.asUIItem()
    }
}