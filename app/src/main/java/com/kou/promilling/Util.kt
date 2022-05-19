package com.kou.promilling

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double.formatResult(): String {
    val dfs = DecimalFormatSymbols(Locale.US)
    val pattern = "0.###"
    val df = DecimalFormat(pattern, dfs)

    return df.format(this)
}