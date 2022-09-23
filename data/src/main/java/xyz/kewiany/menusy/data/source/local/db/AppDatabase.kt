package xyz.kewiany.menusy.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.kewiany.menusy.data.source.local.dao.CacheDao
import xyz.kewiany.menusy.data.source.local.dao.OrderDao
import xyz.kewiany.menusy.data.source.local.dao.ProductDao
import xyz.kewiany.menusy.data.source.local.entity.CachedOrderedProductEntity
import xyz.kewiany.menusy.data.source.local.entity.OrderEntity
import xyz.kewiany.menusy.data.source.local.entity.ProductEntity

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