package ru.tyurin.animesnap.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun timeCodeFormatter(from: Double, to: Double): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    val fromTime = LocalTime.ofSecondOfDay(from.toLong())
    val toTime = LocalTime.ofSecondOfDay(to.toLong())

    val fromFormatted = fromTime.format(formatter)
    val toFormatted = toTime.format(formatter)

    return "$fromFormatted - $toFormatted"
}