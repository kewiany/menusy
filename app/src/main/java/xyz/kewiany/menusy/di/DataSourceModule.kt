package xyz.kewiany.menusy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.database.OrderLocalDataSource
import xyz.kewiany.menusy.data.database.OrderLocalDataSourceImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindsOrderLocalDataSource(impl: OrderLocalDataSourceImpl): OrderLocalDataSource
}