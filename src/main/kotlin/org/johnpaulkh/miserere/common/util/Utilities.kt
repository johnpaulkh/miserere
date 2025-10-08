package org.johnpaulkh.miserere.common.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun String?.toInstant() =
    this?.let {
        LocalDate.parse(this).atStartOfDay(zoneId).toInstant()
    }

fun Instant?.toDateString(): String = this?.let(dateStringFormatter::format) ?: ""

val zoneId: ZoneId = ZoneId.of("Asia/Jakarta")

val dateStringFormatter: DateTimeFormatter =
    DateTimeFormatter
        .ofPattern("dd-MM-yyyy")
        .withZone(zoneId)

fun today(): Instant =
    Instant
        .now()
        .atZone(zoneId)
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)
        .toInstant()

fun tommorow(): Instant = today().plus(1, ChronoUnit.DAYS)
