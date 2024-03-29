package xyz.kewiany.menusy.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import xyz.kewiany.menusy.model.HistoryOrder
import java.time.LocalDateTime

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
    date = LocalDateTime.parse(order.date),
    totalQuantity = order.totalQuantity,
    totalPrice = order.totalPrice.toBigDecimal(),
    products = products.map(ProductEntity::toHistoryProduct),
    placeName = order.placeName,
    placeAddress = order.placeAddress
)