package xyz.kewiany.menusy.ui.utils

interface NavigationDestination {
    val route: String
}

sealed class Screen(override val route: String) : NavigationDestination {
    object WelcomeScreen : Screen("welcome")
    object MenuEntryScreen : Screen("menu_entry")
    object MenuItemsScreen : Screen("menu_entry/$VALUE_BOOLEAN_ARG")
    object OrderScreen : Screen("order")
    object SearchScreen : Screen("search")
}

const val VALUE_BOOLEAN_ARG = "ValueBoolean"