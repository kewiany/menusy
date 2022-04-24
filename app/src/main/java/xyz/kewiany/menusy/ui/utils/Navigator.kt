package xyz.kewiany.menusy.ui.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {
    private val _destination = MutableSharedFlow<NavigationDestination>(extraBufferCapacity = 1)
    val destination = _destination.asSharedFlow()

    fun navigate(destination: NavigationDestination) {
        _destination.tryEmit(destination)
    }
}