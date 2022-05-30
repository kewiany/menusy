package xyz.kewiany.menusy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.kewiany.menusy.utils.DefaultDispatcherProvider
import xyz.kewiany.menusy.utils.DispatcherProvider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindsDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}