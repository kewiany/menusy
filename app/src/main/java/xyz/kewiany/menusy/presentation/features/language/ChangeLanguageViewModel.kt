package xyz.kewiany.menusy.presentation.features.language

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.common.navigation.Navigator
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.Language
import xyz.kewiany.menusy.domain.usecase.language.GetCurrentLanguageUseCase
import xyz.kewiany.menusy.domain.usecase.language.GetLanguagesUseCase
import xyz.kewiany.menusy.domain.usecase.language.SetLanguageUseCase
import xyz.kewiany.menusy.presentation.features.language.ChangeLanguageViewModel.Event
import xyz.kewiany.menusy.presentation.features.language.ChangeLanguageViewModel.State
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    init {
        viewModelScope.launch(dispatcherProvider.main()) {
            val currentLanguage = getCurrentLanguageUseCase()
            updateState {
                it.copy(
                    currentLanguage = currentLanguage,
                    languages = getLanguagesUseCase()
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
                val currentLanguage = requireNotNull(state.value.currentLanguage)
                setLanguageUseCase(currentLanguage)
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
