package xyz.kewiany.menusy.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

    private val _commands = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val commands = _commands.asSharedFlow()

    fun navigate(directions: NavigationCommand) {
        _commands.tryEmit(directions)
    }
}