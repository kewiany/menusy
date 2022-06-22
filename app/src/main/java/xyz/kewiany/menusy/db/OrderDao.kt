package xyz.kewiany.menusy.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {

    @Insert
    suspend fun insert(orderEntity: OrderEntity)

    @Query("SELECT * FROM `order`")
    suspend fun getAll(): List<OrderEntity>
}