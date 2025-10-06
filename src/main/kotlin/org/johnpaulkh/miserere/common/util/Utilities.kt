package org.johnpaulkh.miserere.common.util

import java.time.LocalDate
import java.time.ZoneId


fun String?.toInstant() = this?.let {
    LocalDate.parse(this).atStartOfDay(zoneId).toInstant()
}

val zoneId = ZoneId.of("Asia/Jakarta")