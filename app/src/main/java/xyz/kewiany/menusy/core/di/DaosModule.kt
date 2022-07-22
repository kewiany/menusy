package xyz.kewiany.menusy.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.source.local.dao.OrderDao
import xyz.kewiany.menusy.data.source.local.dao.ProductDao
import xyz.kewiany.menusy.data.source.local.db.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Provides
    fun providesOrderDao(database: AppDatabase): OrderDao = database.orderDao()
}
