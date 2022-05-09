package com.kou.promilling.cuttingwidthcalc

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kou.promilling.calculateCuttingWidth

class CuttingWidthViewModel: ViewModel() {
    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    //Two-way data binding
    val radius = MutableLiveData(Double.MIN_VALUE)
    val roundingRadius = MutableLiveData(Double.MIN_VALUE)
    val cuttingWidth = MutableLiveData(Double.MIN_VALUE)

    @Suppress("UNUSED_PARAMETER")
    fun result(v: View) {
        if (!checkInput()) return

        _result.value = calculateCuttingWidth(
            radius.value!!,
            roundingRadius.value!!,
            cuttingWidth.value!!
        ).toString()
    }

    private fun checkInput(): Boolean {
        if (radius.value == Double.MIN_VALUE ||
            roundingRadius.value == Double.MIN_VALUE ||
            cuttingWidth.value == Double.MIN_VALUE
        ) return false

        return true
    }
}