package xyz.kewiany.menusy

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.usecase.GetMenusUseCase

class MenuEntryViewModelTest : BaseTest() {

    private val navigator = mockk<Navigator>()
    private val getMenusUseCase = mockk<GetMenusUseCase>()

    private val viewModel: MenuEntryViewModel by lazy {
        MenuEntryViewModel(
            navigator,
            getMenusUseCase,
            testDispatcherProvider
        )
    }

    @Test
    fun when_menuClicked_then_navigateToMenuItems() {
        val id = "id"
        viewModel.eventHandler(MenuEntryViewModel.Event.MenuClicked(id))
        verify { navigator.navigate(any()) }
    }
}