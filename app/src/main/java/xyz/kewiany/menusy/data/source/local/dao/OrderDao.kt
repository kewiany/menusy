package xyz.kewiany.menusy.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import xyz.kewiany.menusy.data.source.local.entity.OrderEntity
import xyz.kewiany.menusy.data.source.local.entity.OrderWithProductsEntity

@Dao
interface OrderDao {

    @Insert
    suspend fun insert(orderEntity: OrderEntity): Long

    @Transaction
    @Query("SELECT * FROM `${OrderEntity.TABLE_NAME}`")
    suspend fun getAll(): List<OrderWithProductsEntity>
}