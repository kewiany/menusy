package xyz.kewiany.menusy.viewmodel

import app.cash.turbine.test
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.SettingsRepository
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel.Event
import xyz.kewiany.menusy.ui.language.Language

class ChangeLanguageViewModelTest : BaseTest() {

    private val languages = Language.values().toList()
    private val currentLanguage = Language.ENGLISH
    private val navigator = mockk<Navigator>(relaxed = true)
    private val settingsRepository = mockk<SettingsRepository> {
        coEvery { this@mockk.getLanguages() } returns languages
        coEvery { this@mockk.language } returns MutableStateFlow(currentLanguage)
        coJustRun { setLanguage(any()) }
    }
    private val viewModel: ChangeLanguageViewModel by lazy {
        ChangeLanguageViewModel(
            navigator,
            settingsRepository,
            testDispatcherProvider
        )
    }

    @Test
    fun given_initialization_then_updateCurrentLanguage_and_setLanguages() = testScope.runTest {
        viewModel.state.test {
            skipItems(1)
            val state = awaitItem()
            assertEquals(currentLanguage, state.currentLanguage)
            assertEquals(languages, state.languages)
        }
    }

    @Test
    fun when_languageClicked_then_updateCurrentLanguage() = testScope.runTest {
        val language = Language.UKRAINIAN
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(Event.LanguageClicked(language))
            assertEquals(language, awaitItem().currentLanguage)
        }
    }

    @Test
    fun when_outsideClicked_then_navigateBack() = testScope.runTest {
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(Event.OutsideClicked)
            coVerify { navigator.back() }
        }
    }

    @Test
    fun when_cancelClicked_then_navigateBack() = testScope.runTest {
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(Event.CancelClicked)
            coVerify { navigator.back() }
        }
    }

    @Test
    fun when_languageClicked_and_okClicked_then_setLanguage_and_navigateBack() = testScope.runTest {
        val language = Language.UKRAINIAN
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(Event.LanguageClicked(language))
            awaitItem()
            viewModel.eventHandler(Event.OKClicked)
            runCurrent()
            coVerify { settingsRepository.setLanguage(language) }
            verify { navigator.back() }
        }
    }
}