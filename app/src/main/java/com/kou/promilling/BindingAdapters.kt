package com.kou.promilling

import androidx.databinding.InverseMethod
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.roundToInt

object Converter {
    @InverseMethod("stringToDouble")
    @JvmStatic fun doubleToString(view: TextInputEditText, oldValue: Double, value: Double): String {
        val inView = view.text.toString()
        if (inView.isBlank()) return ""
        val parsed = inView.toDouble()
        if (parsed == value) return inView

        return value.toString()
    }

    @JvmStatic fun stringToDouble(view: TextInputEditText, oldValue: Double, value: String): Double {
        return try {
            value.toDouble()
        } catch (e: Exception) {
            //TODO: add error message
            oldValue
        }
    }

    @InverseMethod("stringToInt")
    @JvmStatic fun intToString(value: Int): String {
        return value.toString()
    }

    @JvmStatic fun stringToInt(value: String): Int {
        //TODO: remove empty string bug
        return try {
            value.toInt()
        } catch (e: Exception) {
            //TODO: remove double value bug
            value.toDouble().roundToInt()
        }
    }
}