package xyz.kewiany.menusy.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.kewiany.menusy.db.OrderEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "product_name")
    val productName: String,
    @ColumnInfo(name = "product_description")
    val productDescription: String,
    @ColumnInfo(name = "product_price")
    val productPrice: String,
    @ColumnInfo(name = "quantity")
    val quantity: Int
) {
    companion object {
        const val TABLE_NAME = "order"
    }
}