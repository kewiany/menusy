package xyz.kewiany.menusy.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import xyz.kewiany.menusy.data.source.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insertAll(productEntities: List<ProductEntity>)
}