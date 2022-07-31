package xyz.kewiany.menusy.viewmodel

import createMenu
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.common.navigation.Navigator
import xyz.kewiany.menusy.domain.usecase.menu.GetMenusUseCase
import xyz.kewiany.menusy.presentation.features.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.presentation.features.menu.entry.MenuEntryViewModel.Event

class MenuEntryViewModelTest : BaseTest() {

    private val navigator = mockk<Navigator>(relaxed = true)
    private val menus = listOf(createMenu(), createMenu())
    private val getMenusUseCase = mockk<GetMenusUseCase> {
        coEvery { this@mockk.invoke() } returns Result.Success(menus)
    }

    private fun viewModel(): MenuEntryViewModel = MenuEntryViewModel(
        navigator,
        getMenusUseCase,
        testDispatcherProvider
    )

    @Test
    fun when_triggerLoadMenusSuccess_then_setMenus_and_setLoading() = testScope.runTest {
        val expectedCount = menus.count()
        val viewModel = viewModel()

        viewModel.eventHandler(Event.TriggerLoadMenus)
        runCurrent()

        val state = viewModel.state.value
        val actualCount = state.menus.count()
        coVerify { getMenusUseCase() }
        assertEquals(expectedCount, actualCount)
        assertFalse(state.showLoading)
    }

    @Test
    fun when_triggerLoadMenusError_then_showError_and_setLoading() = testScope.runTest {
        coEvery { getMenusUseCase() } returns Result.Error()
        val expectedCount = 0
        val viewModel = viewModel()

        viewModel.eventHandler(Event.TriggerLoadMenus)
        runCurrent()

        val state = viewModel.state.value
        val actualCount = state.menus.count()
        coVerify { getMenusUseCase() }
        assertEquals(expectedCount, actualCount)
        assertFalse(state.showLoading)
        assertNotNull(state.showError)
    }

    @Test
    fun when_menuClicked_then_navigateToMenuItems() = testScope.runTest {
        val viewModel = viewModel()
        viewModel.eventHandler(Event.TriggerLoadMenus)
        runCurrent()

        val id = "id"
        viewModel.eventHandler(Event.MenuClicked(id))
        coVerify { navigator.navigate(any()) }
    }
}