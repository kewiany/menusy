package xyz.kewiany.menusy.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class Navigator @Inject constructor() {

    private val _commands = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val commands = _commands.asSharedFlow()

    fun navigate(directions: NavigationCommand) {
        _commands.tryEmit(directions)
    }
}