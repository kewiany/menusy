package xyz.kewiany.menusy.presentation.utils

import org.slf4j.Logger

interface Loggable {
    fun toLog(): String = javaClass.eventClassPrefix + if (isObject) javaClass.simpleName else log()
    fun log(): String = toString()
}

fun Logger.userEvent(event: Any) {
    if (event is Loggable) {
        info(event.toLog())
    }
}

private val Any.isObject: Boolean
    get() = javaClass.declaredFields.any { it.type == javaClass && it.name == "INSTANCE" }

private val <T> Class<T>.eventClassPrefix: String
    get() = try {
        with(canonicalName!!) { substring(indexOf("Event")..lastIndexOf('.')) }
    } catch (e: Exception) {
        ""
    }