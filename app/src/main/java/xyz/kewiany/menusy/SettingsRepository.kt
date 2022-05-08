package xyz.kewiany.menusy

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import xyz.kewiany.menusy.ui.language.Language
import javax.inject.Inject

interface SettingsRepository {
    val language: SharedFlow<Language>
    fun setLanguage(language: Language)
}

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {

    private val _language = MutableStateFlow(Language.ENGLISH)
    override val language = _language.asSharedFlow()

    override fun setLanguage(language: Language) {
        _language.tryEmit(language)
    }
}