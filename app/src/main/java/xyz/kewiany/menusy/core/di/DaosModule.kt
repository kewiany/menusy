package xyz.kewiany.menusy.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.database.AppDatabase
import xyz.kewiany.menusy.data.database.dao.CacheDao
import xyz.kewiany.menusy.data.database.dao.OrderDao
import xyz.kewiany.menusy.data.database.dao.ProductDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Provides
    fun providesOrderDao(database: AppDatabase): OrderDao = database.orderDao()

    @Provides
    fun providesCachedDao(database: AppDatabase): CacheDao = database.cacheDao()

}
