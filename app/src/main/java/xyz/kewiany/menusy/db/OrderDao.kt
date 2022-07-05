package xyz.kewiany.menusy.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface OrderDao {

    @Insert
    suspend fun insert(orderEntity: OrderEntity): Long

    @Transaction
    @Query("SELECT * FROM `${OrderEntity.TABLE_NAME}`")
    suspend fun getAll(): List<OrderWithProducts>
}