package xyz.kewiany.menusy

import xyz.kewiany.menusy.android.common.asUIItem
import xyz.kewiany.menusy.model.Product
import xyz.kewiany.menusy.test.common.createProduct

fun createProductUiItem(
    product: Product = createProduct(),
    quantity: Int = 0
) = product.asUIItem(
    quantity = quantity,
    formatPrice = { "price" }
)