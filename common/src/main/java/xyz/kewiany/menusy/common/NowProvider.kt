package xyz.kewiany.menusy.common

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

private val PL_TIMEZONE: ZoneId = ZoneId.of("Europe/Warsaw")

interface NowProvider {
    val now: LocalDateTime
    val zonedNow: ZonedDateTime
}

val NowProvider.nowInPoland: LocalDateTime
    get() = this.zonedNow.toInstant().atZone(PL_TIMEZONE).toLocalDateTime()

class CalendarNowProvider @Inject constructor() : NowProvider {
    override val now: LocalDateTime
        get() = LocalDateTime.now()
    override val zonedNow: ZonedDateTime
        get() = ZonedDateTime.now()
}