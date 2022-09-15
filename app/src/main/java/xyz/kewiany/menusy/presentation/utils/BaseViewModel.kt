package xyz.kewiany.menusy.presentation.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseViewModel<State : Any, Event : Any>(initialState: State) : ViewModel() {

    protected val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val _state: MutableStateFlow<State> by lazy {
        MutableStateFlow(initialState)
    }
    val state = _state.asStateFlow()

    val eventHandler: (Event) -> Unit = { event ->
        logger.userEvent(event)
        handleEvent(event)
    }
    protected val handler = CoroutineExceptionHandler { _, exception ->
        println(exception.message)
    }

    fun updateState(update: (State) -> State) {
        _state.update(update)
    }

    abstract fun handleEvent(event: Event)
}