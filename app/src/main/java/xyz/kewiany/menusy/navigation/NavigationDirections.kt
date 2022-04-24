package xyz.kewiany.menusy.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object NavigationDirections {

    val welcome = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "welcome"

    }

    val menuEntry = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "menuEntry"
    }

    fun menuItems(menuId: String? = null) = object : NavigationCommand {
        override val arguments = listOf(navArgument(MENU_ID) { type = NavType.StringType })
        override val destination = "menuEntry/$menuId"
    }

    val order = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "order"
    }

    val search = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "search"
    }
}

const val MENU_ID = "menuId"
