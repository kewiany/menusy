package xyz.kewiany.menusy.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.kewiany.menusy.model.Language
import javax.inject.Inject

interface PreferenceDataStore {
    val language: Flow<Language>
    suspend fun setLanguage(language: Language)
}

class PreferenceDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataStore {

    override val language: Flow<Language> = dataStore.data.map { preferences ->
        Language.valueOf(preferences[Keys.LANGUAGE] ?: DEFAULT_LANGUAGE.name)
    }

    override suspend fun setLanguage(language: Language) {
        dataStore.edit { preferences -> preferences[Keys.LANGUAGE] = language.name }
    }

    private object Keys {
        val LANGUAGE = stringPreferencesKey("language")
    }
}

private val DEFAULT_LANGUAGE = Language.ENGLISH