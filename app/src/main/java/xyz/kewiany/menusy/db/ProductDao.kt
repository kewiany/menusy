package xyz.kewiany.menusy.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ProductDao {

    @Insert
    suspend fun insertAll(productEntities: List<ProductEntity>)
}