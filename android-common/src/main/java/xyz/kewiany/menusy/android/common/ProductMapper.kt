package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.PriceFormatter
import xyz.kewiany.menusy.model.Product
import javax.inject.Inject

class ProductMapper @Inject constructor(
    private val priceFormatter: PriceFormatter
) {

    fun asUIItem(quantity: Int = 0, product: Product): ProductUiItem = with(product) {
        ProductUiItem(
            id = id,
            name = name,
            description = description,
            price = priceFormatter.format(price, "PLN"),
            ordered = quantity != 0,
            quantity = quantity
        )
    }
}