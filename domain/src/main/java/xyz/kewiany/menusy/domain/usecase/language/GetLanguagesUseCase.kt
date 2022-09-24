package xyz.kewiany.menusy.domain.usecase.language

import xyz.kewiany.menusy.domain.repository.SettingsRepository
import xyz.kewiany.menusy.model.Language
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    operator fun invoke(): List<Language> {
        return settingsRepository.getLanguages()
    }
}