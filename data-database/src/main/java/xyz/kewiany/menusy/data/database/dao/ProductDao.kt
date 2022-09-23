package xyz.kewiany.menusy.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import xyz.kewiany.menusy.data.database.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insertAll(productEntities: List<ProductEntity>)
}