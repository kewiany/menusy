package xyz.kewiany.menusy.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.data.network.MenuService
import xyz.kewiany.menusy.data.network.PlaceService
import xyz.kewiany.menusy.data.network.ProductService
import xyz.kewiany.menusy.data.network.api.MenuApi
import xyz.kewiany.menusy.data.network.api.PlaceApi
import xyz.kewiany.menusy.data.network.api.ProductApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ApiModule {

    @Singleton
    @Binds
    fun bindsPlaceApi(impl: PlaceService): PlaceApi

    @Singleton
    @Binds
    fun bindsMenuApi(impl: MenuService): MenuApi

    @Singleton
    @Binds
    fun bindsProductsApi(impl: ProductService): ProductApi
}