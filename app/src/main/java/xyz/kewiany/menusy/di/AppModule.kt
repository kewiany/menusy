package xyz.kewiany.menusy.di

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
import xyz.kewiany.menusy.*
import xyz.kewiany.menusy.db.AppDatabase
import xyz.kewiany.menusy.db.OrderDataStore
import xyz.kewiany.menusy.db.OrderDataStoreImpl
import xyz.kewiany.menusy.utils.DefaultDispatcherProvider
import xyz.kewiany.menusy.utils.DispatcherProvider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [AppModule.BindsModule::class])
class AppModule {

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

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class BindsModule {
        @Singleton
        @Binds
        abstract fun bindsDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider

        @Singleton
        @Binds
        abstract fun bindsSearchTextHolder(impl: SearchTextHolderImpl): SearchTextHolder

        @Singleton
        @Binds
        abstract fun bindsOrderDataStore(impl: OrderDataStoreImpl): OrderDataStore

        @Singleton
        @Binds
        abstract fun bindsPreferenceDataStore(impl: PreferenceDataStoreImpl): PreferenceDataStore

        @Singleton
        @Binds
        abstract fun bindsInMemoryDataStore(impl: InMemoryDataStoreImpl): InMemoryDataStore
    }

    companion object {
        private const val PREFERENCES = "preferences"
    }
}