package xyz.kewiany.menusy

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel
import xyz.kewiany.menusy.ui.language.Language

class ChangeLanguageViewModelTest : BaseTest() {

    private val languages = Language.values().toList()
    private val currentLanguage = Language.ENGLISH
    private val navigator = mockk<Navigator>(relaxed = true)
    private val settingsRepository = mockk<SettingsRepository> {
        coEvery { this@mockk.getLanguages() } returns languages
        coEvery { this@mockk.language } returns MutableStateFlow(currentLanguage)
        justRun { setLanguage(any()) }
    }
    private val viewModel: ChangeLanguageViewModel by lazy {
        ChangeLanguageViewModel(
            navigator,
            settingsRepository
        )
    }

    @Test
    fun given_initialization_then_updateCurrentLanguage_and_setLanguages() = testScope.runTest {
        viewModel.state.test {
            skipItems(1)
            val state = awaitItem()
            assert(state.currentLanguage == currentLanguage)
            assert(state.languages == languages)
        }
    }

    @Test
    fun when_languageClicked_then_updateCurrentLanguage() = testScope.runTest {
        val language = Language.UKRAINIAN
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(ChangeLanguageViewModel.Event.LanguageClicked(language))
            assert(awaitItem().currentLanguage == language)
        }
    }

    @Test
    fun when_outsideClicked_then_navigateBack() = testScope.runTest {
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(ChangeLanguageViewModel.Event.OutsideClicked)
            coVerify { navigator.back() }
        }
    }

    @Test
    fun when_cancelClicked_then_navigateBack() = testScope.runTest {
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(ChangeLanguageViewModel.Event.CancelClicked)
            coVerify { navigator.back() }
        }
    }

    @Test
    fun when_languageClicked_and_okClicked_then_setLanguage_and_navigateBack() = testScope.runTest {
        val language = Language.UKRAINIAN
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(ChangeLanguageViewModel.Event.LanguageClicked(language))
            awaitItem()
            viewModel.eventHandler(ChangeLanguageViewModel.Event.OKClicked)
            coVerify { settingsRepository.setLanguage(language) }
            coVerify { navigator.back() }
        }
    }
}