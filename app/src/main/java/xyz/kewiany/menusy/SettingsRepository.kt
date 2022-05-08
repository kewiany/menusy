package xyz.kewiany.menusy

import xyz.kewiany.menusy.ui.language.Language
import javax.inject.Inject

interface SettingsRepository {
    fun getLanguage(): Language
    fun setLanguage(language: Language)
}

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {

    private var currentLanguage: Language = Language.ENGLISH

    override fun getLanguage(): Language = currentLanguage

    override fun setLanguage(language: Language) {
        currentLanguage = language
    }
}