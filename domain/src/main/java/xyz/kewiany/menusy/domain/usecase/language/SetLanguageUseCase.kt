package xyz.kewiany.menusy.domain.usecase.language

import xyz.kewiany.menusy.domain.repository.SettingsRepository
import xyz.kewiany.menusy.model.Language
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(language: Language) {
        return settingsRepository.setLanguage(language)
    }
}