package xyz.kewiany.menusy.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import xyz.kewiany.menusy.domain.model.HistoryOrder
import xyz.kewiany.menusy.domain.model.HistoryProduct

data class OrderWithProductsEntity(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = PARENT_COLUMN,
        entityColumn = ENTITY_COLUMN
    )
    val products: List<ProductEntity>
) {
    companion object {
        const val PARENT_COLUMN = "id"
        const val ENTITY_COLUMN = "order_id"
    }
}

fun OrderWithProductsEntity.toHistoryOrder() = HistoryOrder(
    orderId = order.id,
    date = order.date,
    totalPrice = order.totalPrice,
    totalQuantity = order.totalQuantity,
    products = products.map(ProductEntity::toHistoryProduct)
)

fun ProductEntity.toHistoryProduct() = HistoryProduct(
    productId = id,
    name = name,
    description = description,
    price = price.toFloat(),
    quantity = quantity
)