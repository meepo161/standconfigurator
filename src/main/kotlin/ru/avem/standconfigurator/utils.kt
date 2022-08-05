package ru.avem.standconfigurator

import java.util.*
import kotlin.math.abs

fun Double.autoformat(): String {
    val absNum = abs(this)

    var format = "%.0f"
    when {
        absNum == 0.0 -> format = "%.0f"
        absNum < 0.1f -> format = "%.5f"
        absNum < 1f -> format = "%.4f"
        absNum < 10f -> format = "%.3f"
        absNum < 100f -> format = "%.2f"
        absNum < 1000f -> format = "%.1f"
        absNum < 10000f -> format = "%.0f"
    }
    return String.format(Locale.US, format, this)
}

fun Float.autoformat() = this.toDouble().autoformat()
