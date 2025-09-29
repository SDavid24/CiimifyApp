package com.newagedavid.climifyapp.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object DateFormats {
    val savedAtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")
    val ForecastDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")
    val ForcaseDateUILabelFormat = DateTimeFormatter.ofPattern("dd/MM EEE")
}


fun getCurrentFormattedTime(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")
    return LocalDateTime.now().format(formatter)
}

fun getStartOfTheDay(): String {
    return LocalDate.now().atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}
