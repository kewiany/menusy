package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.model.Category
import xyz.kewiany.menusy.model.OrderedProduct
import xyz.kewiany.menusy.model.Product
import javax.inject.Inject

class ContentBuilder @Inject constructor(
    private val productMapper: ProductMapper
) {

    fun buildContent(
        categories: List<Category>,
        products: List<Product>,
        orderedProducts: List<OrderedProduct>
    ): List<UiItem> {
        val items = mutableListOf<UiItem>()

        val groupedProductsByCategory = products.groupBy { it.categoryId }.entries
        groupedProductsByCategory.forEach { (categoryId, products) ->
            val category = categories.find { it.id == categoryId }
            if (category != null) {
                items.add(category.asUIItem())
            }

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
                productMapper.asUIItem(orderedQuantity, product)
            } else {
                productMapper.asUIItem(product = product)
            }
        }
    }
}