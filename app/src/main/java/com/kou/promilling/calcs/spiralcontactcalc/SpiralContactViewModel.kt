@file:Suppress("unused")

package com.kou.promilling.calcs.spiralcontactcalc

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.promilling.calculateSpiralContact
import com.kou.promilling.database.DatabaseSpiralContactLength
import com.kou.promilling.database.MillingDao
import com.kou.promilling.formatResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SpiralContactViewModel(
    private val database: MillingDao,
    item: DatabaseSpiralContactLength?
): ViewModel() {

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

    init {
        item?.let {
            diameter.value = it.toolDiameter
            spiralAngle.value = it.spiralAngle
            cuttingHeight.value = it.cuttingDepth
            cuttingWidth.value = it.cuttingWidth
            fluteCount.value = it.fluteCount
            flutePosition.value = it.flutePosition
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun result(v: View) {
        if (!checkInput()) return

        val diameter = diameter.value!!
        val spiralAngle = spiralAngle.value!!
        val cuttingHeight = cuttingHeight.value!!
        val cuttingWidth = cuttingWidth.value!!
        val fluteCount = fluteCount.value!!
        val flutePosition = flutePosition.value!!
        val result = calculateSpiralContact(
            diameter,
            spiralAngle,
            cuttingHeight,
            cuttingWidth,
            fluteCount,
            flutePosition
        )

        //TODO: make ViewModel an AndroidViewModel and extract this to string resources
        _result.value = "${result.formatResult()} mm"

        viewModelScope.launch {
            writeToDatabase(result = result)
        }

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

    private suspend fun writeToDatabase(
        timeMillis: Long = System.currentTimeMillis(),
        diameter: Double = this.diameter.value!!,
        spiralAngle: Double = this.spiralAngle.value!!,
        cuttingHeight: Double = this.cuttingHeight.value!!,
        cuttingWidth: Double = this.cuttingWidth.value!!,
        fluteCount: Int = this.fluteCount.value!!,
        flutePosition: Double = this.flutePosition.value!!,
        result: Double
    ) {
        val entry = DatabaseSpiralContactLength(
            date = Date(timeMillis),
            toolDiameter = diameter,
            spiralAngle = spiralAngle,
            cuttingDepth = cuttingHeight,
            cuttingWidth = cuttingWidth,
            fluteCount = fluteCount,
            flutePosition = flutePosition,
            result = result
        )
        withContext(Dispatchers.IO) {
            database.insertEntry(entry)
        }
    }
}