package xyz.kewiany.menusy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.datastore.PlaceDataStore
import xyz.kewiany.menusy.data.datastore.PlaceDataStoreImpl
import xyz.kewiany.menusy.data.datastore.PreferenceDataStore
import xyz.kewiany.menusy.data.datastore.PreferenceDataStoreImpl
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
}