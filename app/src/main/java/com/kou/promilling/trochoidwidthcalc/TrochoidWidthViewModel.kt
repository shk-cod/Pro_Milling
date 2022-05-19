package com.kou.promilling.trochoidwidthcalc

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kou.promilling.calculateTrochoidWidth
import com.kou.promilling.formatResult

class TrochoidWidthViewModel: ViewModel() {
    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    //Two-way data binding
    val radius = MutableLiveData(Double.MIN_VALUE)
    val roundingRadius = MutableLiveData(Double.MIN_VALUE)
    val trochoidStep = MutableLiveData(Double.MIN_VALUE)

    @Suppress("UNUSED_PARAMETER")
    fun result(v: View) {
        if (!checkInput()) return

        _result.value = calculateTrochoidWidth(
            radius.value!!,
            roundingRadius.value!!,
            trochoidStep.value!!
        ).formatResult()
    }

    private fun checkInput(): Boolean {
        if (
            radius.value == Double.MIN_VALUE ||
            roundingRadius.value == Double.MIN_VALUE ||
            trochoidStep.value == Double.MIN_VALUE
        ) return false

        return true
    }
}