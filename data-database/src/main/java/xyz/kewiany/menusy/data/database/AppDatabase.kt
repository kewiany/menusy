package xyz.kewiany.menusy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.kewiany.menusy.data.database.dao.CacheDao
import xyz.kewiany.menusy.data.database.dao.OrderDao
import xyz.kewiany.menusy.data.database.dao.ProductDao
import xyz.kewiany.menusy.data.database.entity.CachedOrderedProductEntity
import xyz.kewiany.menusy.data.database.entity.OrderEntity
import xyz.kewiany.menusy.data.database.entity.ProductEntity

@Database(
    entities = [
        OrderEntity::class,
        ProductEntity::class,
        CachedOrderedProductEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun productDao(): ProductDao
    abstract fun cacheDao(): CacheDao
}