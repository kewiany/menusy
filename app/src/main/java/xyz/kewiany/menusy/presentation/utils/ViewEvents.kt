package xyz.kewiany.menusy.presentation.utils

import java.util.concurrent.atomic.AtomicBoolean

open class SingleEvent<out T>(private val argument: T) {
    private val isConsumed = AtomicBoolean(false)

    private fun isConsumed() = isConsumed.getAndSet(true)

    val payloadIfNotConsumed get() = argument.takeUnless { isConsumed() }

    inline fun consume(action: (T) -> Unit) {
        payloadIfNotConsumed?.let(action)
    }
}