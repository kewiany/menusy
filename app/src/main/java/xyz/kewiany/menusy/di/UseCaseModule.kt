package xyz.kewiany.menusy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.usecase.GetMenusUseCase
import xyz.kewiany.menusy.usecase.GetMenusUseCaseImpl
import xyz.kewiany.menusy.usecase.GetProductsUseCase
import xyz.kewiany.menusy.usecase.GetProductsUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindsGetMenusUseCase(impl: GetMenusUseCaseImpl): GetMenusUseCase

    @Singleton
    @Binds
    abstract fun bindsGetProductsUseCase(impl: GetProductsUseCaseImpl): GetProductsUseCase
}