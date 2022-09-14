package xyz.kewiany.menusy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.source.local.*
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface DataStoreModule {

    @Singleton
    @Binds
    fun bindsPlaceDataStore(impl: PlaceDataStoreImpl): PlaceDataStore

    @Singleton
    @Binds
    fun bindsPreferenceDataStore(impl: PreferenceDataStoreImpl): PreferenceDataStore

    @Singleton
    @Binds
    fun bindsOrderDataStore(impl: OrderDataStoreImpl): OrderDataStore

    @Singleton
    @Binds
    fun bindsOrderedProductsDataStore(impl: OrderedProductsDataStoreImpl): OrderedProductsDataStore
}