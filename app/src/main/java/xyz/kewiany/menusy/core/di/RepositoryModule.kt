package xyz.kewiany.menusy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.*
import xyz.kewiany.menusy.domain.repository.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository

    @Singleton
    @Binds
    fun bindsSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Singleton
    @Binds
    fun bindsOrderRepository(impl: OrderRepositoryImpl): OrderRepository

    @Singleton
    @Binds
    fun bindsMenuRepository(impl: MenuRepositoryImpl): MenuRepository

    @Singleton
    @Binds
    fun bindsProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Singleton
    @Binds
    fun bindsSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Singleton
    @Binds
    fun bindsPlaceRepository(impl: PlaceRepositoryImpl): PlaceRepository
}