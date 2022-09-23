package xyz.kewiany.menusy.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import xyz.kewiany.menusy.data.database.entity.OrderEntity
import xyz.kewiany.menusy.data.database.entity.OrderWithProductsEntity

@Dao
interface OrderDao {

    @Insert
    suspend fun insert(orderEntity: OrderEntity): Long

    @Transaction
    @Query("SELECT * FROM `${OrderEntity.TABLE_NAME}`")
    suspend fun getAll(): List<OrderWithProductsEntity>
}