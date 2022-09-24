package xyz.kewiany.menusy.domain.usecase.language

import kotlinx.coroutines.flow.first
import xyz.kewiany.menusy.domain.repository.SettingsRepository
import xyz.kewiany.menusy.model.Language
import javax.inject.Inject

class GetCurrentLanguageUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(): Language {
        return settingsRepository.language.first()
    }
}