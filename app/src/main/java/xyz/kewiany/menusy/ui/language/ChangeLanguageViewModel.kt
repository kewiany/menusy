package xyz.kewiany.menusy.ui.language

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.SettingsRepository
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel.Event
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import xyz.kewiany.menusy.utils.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    private val navigator: Navigator,
    private val settingsRepository: SettingsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    init {
        viewModelScope.launch(dispatcherProvider.main()) {
            val currentLanguage = settingsRepository.language.first()
            updateState {
                it.copy(
                    currentLanguage = currentLanguage,
                    languages = settingsRepository.getLanguages()
                )
            }
        }
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.LanguageClicked -> {
            updateState { it.copy(currentLanguage = event.language) }
        }
        is Event.OutsideClicked -> {
            navigator.back()
        }
        is Event.CancelClicked -> {
            navigator.back()
        }
        is Event.OKClicked -> {
            viewModelScope.launch(dispatcherProvider.main()) {
                settingsRepository.setLanguage(requireNotNull(state.value.currentLanguage))
            }
            navigator.back()
        }
    }

    data class State(
        val currentLanguage: Language? = null,
        val languages: List<Language> = emptyList()
    )

    sealed class Event {
        data class LanguageClicked(val language: Language) : Event()
        object OutsideClicked : Event()
        object CancelClicked : Event()
        object OKClicked : Event()
    }
}
