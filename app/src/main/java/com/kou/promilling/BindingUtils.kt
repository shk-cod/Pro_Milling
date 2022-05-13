package com.kou.promilling

import androidx.databinding.InverseMethod
import com.google.android.material.textfield.TextInputEditText

object Converter {
    @InverseMethod("stringToDouble")
    @Suppress("UNUSED_PARAMETER")
    @JvmStatic fun doubleToString(view: TextInputEditText, oldValue: Double, value: Double): String {
        val inView = view.text.toString()
        if (inView.isBlank()) return ""

        try {
            val parsed = inView.toDouble()
            if (parsed == value) return inView
        } catch (e: NumberFormatException) {
            return ""
        }

        return value.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    @JvmStatic fun stringToDouble(view: TextInputEditText, oldValue: Double, value: String): Double {
        return try {
            value.toDouble()
        } catch (e: NumberFormatException) {
            if (value.isBlank()) Double.MIN_VALUE else oldValue
        }
    }

    @InverseMethod("stringToInt")
    @Suppress("UNUSED_PARAMETER")
    @JvmStatic fun intToString(view: TextInputEditText, oldValue: Int, value: Int): String {
        val inView = view.text.toString()
        if (inView.isBlank()) return ""

        try {
            val parsed = inView.toInt()
            if (parsed == value) return inView
        } catch (e: NumberFormatException) {
            return ""
        }

        return value.toString()
    }

    @Suppress("UNUSED_PARAMETER")
    @JvmStatic fun stringToInt(view: TextInputEditText, oldValue: Int, value: String): Int {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            if (value.isBlank()) Int.MIN_VALUE else oldValue
        }
    }
}