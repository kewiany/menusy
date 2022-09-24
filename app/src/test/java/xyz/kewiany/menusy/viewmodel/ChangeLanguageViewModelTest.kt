package xyz.kewiany.menusy.viewmodel

import io.mockk.*
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.android.common.navigation.Navigator
import xyz.kewiany.menusy.domain.model.Language
import xyz.kewiany.menusy.domain.usecase.language.GetCurrentLanguageUseCase
import xyz.kewiany.menusy.domain.usecase.language.GetLanguagesUseCase
import xyz.kewiany.menusy.domain.usecase.language.SetLanguageUseCase
import xyz.kewiany.menusy.presentation.features.language.ChangeLanguageViewModel
import xyz.kewiany.menusy.presentation.features.language.ChangeLanguageViewModel.Event
import xyz.kewiany.menusy.test.common.BaseTest

class ChangeLanguageViewModelTest : BaseTest() {

    private val languages = Language.values().toList()
    private val currentLanguage = Language.ENGLISH
    private val language = Language.UKRAINIAN

    private val navigator = mockk<Navigator>(relaxed = true)
    private val getCurrentLanguageUseCase = mockk<GetCurrentLanguageUseCase> {
        coEvery { this@mockk.invoke() } returns currentLanguage
    }
    private val getLanguagesUseCase = mockk<GetLanguagesUseCase> {
        every { this@mockk.invoke() } returns languages
    }
    private val setLanguageUseCase = mockk<SetLanguageUseCase> {
        coJustRun { this@mockk.invoke(any()) }
    }

    private fun viewModel() = ChangeLanguageViewModel(
        navigator,
        getCurrentLanguageUseCase,
        getLanguagesUseCase,
        setLanguageUseCase,
        testDispatcherProvider
    )

    @Test
    fun given_initialization_then_setCurrentLanguage_and_setLanguages() = testScope.runTest {
        val viewModel = viewModel()
        val state = viewModel.state
        val expectedLanguage = currentLanguage
        val expectedLanguages = languages

        runCurrent()

        val actualLanguage = state.value.currentLanguage
        val actualLanguages = state.value.languages
        assertEquals(expectedLanguage, actualLanguage)
        assertEquals(expectedLanguages, actualLanguages)
    }

    @Test
    fun when_languageClicked_then_setCurrentLanguage() = testScope.runTest {
        val viewModel = viewModel()
        val state = viewModel.state
        val expectedLanguage = language

        viewModel.eventHandler(Event.LanguageClicked(language))

        val actualLanguage = state.value.currentLanguage
        assertEquals(expectedLanguage, actualLanguage)
    }

    @Test
    fun when_outsideClicked_then_navigateBack() = testScope.runTest {
        val viewModel = viewModel()
        viewModel.eventHandler(Event.OutsideClicked)

        coVerify { navigator.back() }
    }

    @Test
    fun when_cancelClicked_then_navigateBack() = testScope.runTest {
        val viewModel = viewModel()
        viewModel.eventHandler(Event.CancelClicked)

        coVerify { navigator.back() }
    }

    @Test
    fun when_languageClicked_and_okClicked_then_setLanguage_and_navigateBack() = testScope.runTest {
        val viewModel = viewModel()
        runCurrent()
        viewModel.eventHandler(Event.LanguageClicked(language))
        viewModel.eventHandler(Event.OKClicked)
        runCurrent()

        coVerify { setLanguageUseCase(language) }
        verify { navigator.back() }
    }
}