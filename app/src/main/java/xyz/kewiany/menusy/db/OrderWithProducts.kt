package xyz.kewiany.menusy.db

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithProducts(
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