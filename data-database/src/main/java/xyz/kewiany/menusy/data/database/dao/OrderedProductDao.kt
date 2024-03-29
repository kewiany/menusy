package xyz.kewiany.menusy.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.data.database.entity.CachedOrderedProductEntity

@Dao
interface OrderedProductDao {

    @Query("SELECT `quantity` FROM `${CachedOrderedProductEntity.TABLE_NAME}`")
    fun getQuantity(): Flow<List<Int>>

    @Transaction
    @Query("SELECT * FROM `${CachedOrderedProductEntity.TABLE_NAME}`")
    suspend fun getAll(): List<CachedOrderedProductEntity>

    @Insert
    suspend fun insertAll(orderedProducts: List<CachedOrderedProductEntity>)

    @Query("DELETE FROM `${CachedOrderedProductEntity.TABLE_NAME}`")
    suspend fun deleteAll()
}