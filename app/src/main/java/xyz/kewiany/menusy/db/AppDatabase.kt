package xyz.kewiany.menusy.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.kewiany.menusy.ProductEntitiesConverter

@Database(entities = [OrderEntity::class], version = 1)
@TypeConverters(ProductEntitiesConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}