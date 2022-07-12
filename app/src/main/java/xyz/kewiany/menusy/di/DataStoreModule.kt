package xyz.kewiany.menusy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.InMemoryDataStore
import xyz.kewiany.menusy.InMemoryDataStoreImpl
import xyz.kewiany.menusy.PreferenceDataStore
import xyz.kewiany.menusy.PreferenceDataStoreImpl
import xyz.kewiany.menusy.db.OrderDataStore
import xyz.kewiany.menusy.db.OrderDataStoreImpl
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun bindsOrderDataStore(impl: OrderDataStoreImpl): OrderDataStore

    @Singleton
    @Binds
    abstract fun bindsPreferenceDataStore(impl: PreferenceDataStoreImpl): PreferenceDataStore

    @Singleton
    @Binds
    abstract fun bindsInMemoryDataStore(impl: InMemoryDataStoreImpl): InMemoryDataStore
}