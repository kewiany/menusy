package xyz.kewiany.menusy.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.kewiany.menusy.db.ProductEntity.Companion.TABLE_NAME

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
    val price: String,
    @ColumnInfo(name = "quantity")
    val quantity: Int
) {
    companion object {
        const val TABLE_NAME = "products"
    }
}