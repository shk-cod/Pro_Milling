package com.kou.promilling

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import timber.log.Timber

object Converter {
    @InverseMethod("stringToDouble")
    @Suppress("UNUSED_PARAMETER")
    @JvmStatic fun doubleToString(view: TextInputEditText, oldValue: Double, value: Double): String {
        val inView = view.text.toString()
        if (inView.isBlank()) {
            return if (value == Double.MIN_VALUE) "" else value.formatResult()
        }

        try {
            val parsed = inView.toDouble()
            if (parsed == value) return inView
        } catch (e: NumberFormatException) {
            return ""
        }

        return value.formatResult()
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
        if (inView.isBlank()) {
            return if (value == Int.MIN_VALUE) "" else value.toString()
        }

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

@BindingAdapter("android:src")
fun setImageViewResource(view: ImageView, imageId: Int?) {
    imageId?.let {
        Glide.with(view.context)
            .load(imageId)
            .into(view)
    }
    Timber.i("Glide binding image", imageId, view.toString())
}