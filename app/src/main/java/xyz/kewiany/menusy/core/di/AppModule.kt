package xyz.kewiany.menusy.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import xyz.kewiany.menusy.core.DefaultDispatcherProvider
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.data.source.local.*
import xyz.kewiany.menusy.data.source.local.db.AppDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [AppModule.BindsModule::class])
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(PREFERENCES) })
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        val database = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "database")
        return database.build()
    }

    private const val PREFERENCES = "preferences"

    @InstallIn(SingletonComponent::class)
    @Module
    interface BindsModule {
        @Singleton
        @Binds
        fun bindsDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider

        @Singleton
        @Binds
        fun bindsPreferenceDataStore(impl: PreferenceDataStoreImpl): PreferenceDataStore

        @Singleton
        @Binds
        fun bindsInMemoryDataHolder(impl: InMemoryDataHolderImpl): InMemoryDataHolder

        @Singleton
        @Binds
        fun bindsSearchTextHolder(impl: SearchTextHolderImpl): SearchTextHolder
    }
}