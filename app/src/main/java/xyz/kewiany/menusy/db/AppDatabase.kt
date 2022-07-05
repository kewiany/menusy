package xyz.kewiany.menusy.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OrderEntity::class, ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun productDao(): ProductDao
}