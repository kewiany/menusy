package xyz.kewiany.menusy.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.kewiany.menusy.data.database.entity.ProductEntity.Companion.TABLE_NAME
import xyz.kewiany.menusy.domain.model.HistoryProduct
import xyz.kewiany.menusy.domain.model.OrderedProduct

@Entity(tableName = TABLE_NAME)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "order_id")
    val orderId: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "price")
    val price: Float,
    @ColumnInfo(name = "quantity")
    val quantity: Int
) {
    companion object {
        const val TABLE_NAME = "products"
    }
}

fun ProductEntity.toHistoryProduct() = HistoryProduct(
    productId = id,
    name = name,
    description = description,
    price = price.toBigDecimal(),
    quantity = quantity
)

fun OrderedProduct.toProductEntity(orderId: Long) = ProductEntity(
    orderId = orderId,
    name = product.name,
    description = product.description,
    price = product.price.toFloat(),
    quantity = quantity,
)