package xyz.kewiany.menusy.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import xyz.kewiany.menusy.android.common.navigation.Navigator
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.usecase.menu.GetPlaceUseCase
import xyz.kewiany.menusy.feature.menu.entry.MenuEntryViewModel.Event
import xyz.kewiany.menusy.test.common.BaseTest
import xyz.kewiany.menusy.test.common.createMenu
import xyz.kewiany.menusy.test.common.createPlace

class MenuEntryViewModelTest : BaseTest() {

    private val navigator = mockk<Navigator>(relaxed = true)
    private val place = createPlace()
    private val menus = listOf(createMenu(), createMenu())
    private val getPlaceUseCase = mockk<GetPlaceUseCase> {
        coEvery { this@mockk.invoke(any()) } returns Result.Success(place)
    }
    private val getMenusUseCase = mockk<GetMenusUseCase> {
        coEvery { this@mockk.invoke(any()) } returns Result.Success(menus)
    }

    private fun viewModel(): xyz.kewiany.menusy.feature.menu.entry.MenuEntryViewModel =
        xyz.kewiany.menusy.feature.menu.entry.MenuEntryViewModel(
            navigator,
            getPlaceUseCase,
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
        coVerify { getMenusUseCase(any()) }
        assertEquals(expectedCount, actualCount)
        assertFalse(state.showLoading)
    }

    @Test
    fun when_triggerLoadMenusError_then_showError_and_setLoading() = testScope.runTest {
        coEvery { getMenusUseCase(any()) } returns Result.Error()
        val expectedCount = 0
        val viewModel = viewModel()

        viewModel.eventHandler(Event.TriggerLoadMenus)
        runCurrent()

        val state = viewModel.state.value
        val actualCount = state.menus.count()
        coVerify { getMenusUseCase(any()) }
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