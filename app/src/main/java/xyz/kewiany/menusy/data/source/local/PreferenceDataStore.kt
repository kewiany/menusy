package xyz.kewiany.menusy.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.kewiany.menusy.domain.model.Language
import javax.inject.Inject

interface PreferenceDataStore {
    val language: Flow<Language>
    suspend fun setLanguage(language: Language)
}

class PreferenceDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataStore {

    override val language: Flow<Language> = dataStore.data.map { preferences ->
        Language.valueOf(preferences[PreferencesKeys.LANGUAGE] ?: DEFAULT_LANGUAGE.name)
    }

    override suspend fun setLanguage(language: Language) {
        dataStore.edit { preferences -> preferences[PreferencesKeys.LANGUAGE] = language.name }
    }
}

private object PreferencesKeys {
    val LANGUAGE = stringPreferencesKey("language")
}

private val DEFAULT_LANGUAGE = Language.ENGLISH