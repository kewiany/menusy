package xyz.kewiany.menusy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.source.local.*
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun bindsOrderDataSource(impl: OrderDataSourceImpl): OrderDataSource

    @Singleton
    @Binds
    abstract fun bindsPreferenceDataStore(impl: PreferenceDataStoreImpl): PreferenceDataStore

    @Singleton
    @Binds
    abstract fun bindsInMemoryDataHolder(impl: InMemoryDataHolderImpl): InMemoryDataHolder
}