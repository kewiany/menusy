package xyz.kewiany.menusy

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.kewiany.menusy.ui.language.Language
import javax.inject.Inject

interface SettingsRepository {
    val language: Flow<Language>
    fun getLanguages(): List<Language>
    suspend fun setLanguage(language: Language)
}

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    override val language: Flow<Language> = dataStore.data.map { preferences ->
        Language.valueOf(preferences[PreferencesKeys.LANGUAGE] ?: Language.ENGLISH.name)
    }

    override fun getLanguages(): List<Language> = LANGUAGES

    override suspend fun setLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = language.name
        }
    }

    companion object {
        private val LANGUAGES = Language.values().toList()
    }
}