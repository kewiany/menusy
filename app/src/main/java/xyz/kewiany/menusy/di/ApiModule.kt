package xyz.kewiany.menusy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.api.MenuApi
import xyz.kewiany.menusy.api.MenuService
import xyz.kewiany.menusy.api.ProductApi
import xyz.kewiany.menusy.api.ProductService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ApiModule {

    @Singleton
    @Binds
    abstract fun bindsMenuApi(impl: MenuService): MenuApi

    @Singleton
    @Binds
    abstract fun bindsProductsApi(impl: ProductService): ProductApi
}