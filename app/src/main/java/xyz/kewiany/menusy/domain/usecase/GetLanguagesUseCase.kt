package xyz.kewiany.menusy.domain.usecase

import xyz.kewiany.menusy.domain.model.Language
import xyz.kewiany.menusy.domain.repository.SettingsRepository
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    operator fun invoke(): List<Language> {
        return settingsRepository.getLanguages()
    }
}