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
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Singleton
    @Binds
    abstract fun bindsOrderRepository(impl: OrderRepositoryImpl): OrderRepository

    @Singleton
    @Binds
    abstract fun bindsMenuRepository(impl: MenuRepositoryImpl): MenuRepository

    @Singleton
    @Binds
    abstract fun bindsSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}