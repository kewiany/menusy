package xyz.kewiany.menusy.data

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.data.datastore.PreferenceDataStore
import xyz.kewiany.menusy.domain.model.Language
import xyz.kewiany.menusy.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) : SettingsRepository {

    override val language: Flow<Language> = preferenceDataStore.language

    override fun getLanguages(): List<Language> = LANGUAGES

    override suspend fun setLanguage(language: Language) {
        preferenceDataStore.setLanguage(language)
    }

    companion object {
        private val LANGUAGES = Language.values().toList()
    }
}