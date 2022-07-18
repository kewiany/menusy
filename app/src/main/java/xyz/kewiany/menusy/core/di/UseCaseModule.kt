package xyz.kewiany.menusy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.domain.usecase.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindsGetMenuUseCase(impl: GetMenuUseCaseImpl): GetMenuUseCase

    @Singleton
    @Binds
    abstract fun bindsGetMenusUseCase(impl: GetMenusUseCaseImpl): GetMenusUseCase

    @Singleton
    @Binds
    abstract fun bindsGetProductsUseCase(impl: GetProductsUseCaseImpl): GetProductsUseCase
}