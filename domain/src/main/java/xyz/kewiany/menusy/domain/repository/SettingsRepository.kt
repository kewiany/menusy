package xyz.kewiany.menusy.domain.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.model.Language

interface SettingsRepository {
    val language: Flow<Language>
    fun getLanguages(): List<Language>
    suspend fun setLanguage(language: Language)
}