package xyz.kewiany.menusy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.source.local.OrderDataSource
import xyz.kewiany.menusy.data.source.local.OrderDataSourceImpl
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface DataStoreModule {

    @Singleton
    @Binds
    fun bindsOrderDataSource(impl: OrderDataSourceImpl): OrderDataSource
}