package xyz.kewiany.menusy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.*
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
}