package xyz.kewiany.menusy.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.kewiany.menusy.data.database.entity.OrderEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "total_quantity")
    val totalQuantity: Int,
    @ColumnInfo(name = "total_price")
    val totalPrice: Float,
    @ColumnInfo(name = "place_name")
    val placeName: String,
    @ColumnInfo(name = "place_address")
    val placeAddress: String
) {
    companion object {
        const val TABLE_NAME = "order"
    }
}