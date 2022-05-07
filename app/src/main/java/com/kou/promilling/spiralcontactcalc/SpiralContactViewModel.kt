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
    val diameter = MutableLiveData<Double>(0.0)
    val spiralAngle = MutableLiveData<Double>(0.0)
    val cuttingHeight = MutableLiveData<Double>(0.0)
    val cuttingWidth = MutableLiveData<Double>(0.0)
    val fluteCount = MutableLiveData<Int>(0)
    val flutePosition = MutableLiveData<Double>(0.0)


    //TODO: add field errors
    private val _cuttingHeightError = MutableLiveData<String>()
    val cuttingHeightError: LiveData<String>
        get() = _cuttingHeightError

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
        if (diameter.value == 0.0 ||
                spiralAngle.value == 0.0 ||
                cuttingHeight.value == 0.0 ||
                cuttingWidth.value == 0.0 ||
                fluteCount.value == 0) return false

//        if (cuttingHeight.value > _diameter.value) {

//        }
        return true
    }
}