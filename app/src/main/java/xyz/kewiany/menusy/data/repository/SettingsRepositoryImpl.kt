package xyz.kewiany.menusy.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.PreferenceDataStore
import xyz.kewiany.menusy.domain.repository.SettingsRepository
import xyz.kewiany.menusy.ui.language.Language
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