package com.kou.promilling.spiralcontactcalc

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kou.promilling.calculateSpiralContact

class SpiralContactViewModel: ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    //Two-way data binding
    val diameter = MutableLiveData(Double.MIN_VALUE)
    val spiralAngle = MutableLiveData(Double.MIN_VALUE)
    val cuttingHeight = MutableLiveData(Double.MIN_VALUE)
    val cuttingWidth = MutableLiveData(Double.MIN_VALUE)
    val fluteCount = MutableLiveData(Int.MIN_VALUE)
    val flutePosition = MutableLiveData(Double.MIN_VALUE)


    //TODO: add field errors
    private val _cuttingHeightError = MutableLiveData<String>()
    val cuttingHeightError: LiveData<String>
        get() = _cuttingHeightError

    @Suppress("UNUSED_PARAMETER")
    fun result(v: View) {
        if (!checkInput()) return

        _result.value = calculateSpiralContact(
            diameter.value!!,
            spiralAngle.value!!,
            cuttingHeight.value!!,
            cuttingWidth.value!!,
            fluteCount.value!!,
            flutePosition.value!!
        ).toString()
    }

    private fun checkInput(): Boolean {
        if (
            diameter.value == Double.MIN_VALUE ||
            spiralAngle.value == Double.MIN_VALUE ||
            cuttingHeight.value == Double.MIN_VALUE ||
            cuttingWidth.value == Double.MIN_VALUE ||
            fluteCount.value == Int.MIN_VALUE ||
            flutePosition.value == Double.MIN_VALUE
        ) return false

//        if (cuttingHeight.value > _diameter.value) {

//        }
        return true
    }
}