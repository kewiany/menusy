package xyz.kewiany.menusy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.source.remote.MenuService
import xyz.kewiany.menusy.data.source.remote.ProductService
import xyz.kewiany.menusy.data.source.remote.api.MenuApi
import xyz.kewiany.menusy.data.source.remote.api.ProductApi
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