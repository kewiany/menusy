package xyz.kewiany.menusy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.repository.MenuRepositoryImpl
import xyz.kewiany.menusy.data.repository.OrderRepositoryImpl
import xyz.kewiany.menusy.data.repository.SearchRepositoryImpl
import xyz.kewiany.menusy.data.repository.SettingsRepositoryImpl
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.domain.repository.OrderRepository
import xyz.kewiany.menusy.domain.repository.SearchRepository
import xyz.kewiany.menusy.domain.repository.SettingsRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

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
    fun bindsSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}