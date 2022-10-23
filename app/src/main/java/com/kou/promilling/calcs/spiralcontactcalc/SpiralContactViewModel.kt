@file:Suppress("unused")

package com.kou.promilling.calcs.spiralcontactcalc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kou.promilling.R
import com.kou.promilling.calculateSpiralContact
import com.kou.promilling.database.EntityType
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SpiralContactViewModel(
    private val database: MillingDao,
    item: ResultItem?,
    private val app: Application
): AndroidViewModel(app) {

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


    init {
        item?.let { //if navigates from the details screen
            diameter.value = it.toolDiameter
            spiralAngle.value = it.spiralAngle
            cuttingHeight.value = it.cuttingDepth
            cuttingWidth.value = it.cuttingWidth
            fluteCount.value = it.fluteCount
            flutePosition.value = it.flutePosition
        }
    }

    /**
     * Writes calculated result to the database.
     *
     * @return false if checking the input has failed,
     * otherwise true.
     */
    fun result(): Boolean {
        if (!checkInput()) return false

        val result = calculateSpiralContact(
            diameter.value!!,
            spiralAngle.value!!,
            cuttingHeight.value!!,
            cuttingWidth.value!!,
            fluteCount.value!!,
            flutePosition.value!!
        )

        //formatting the result
        _result.value = app.applicationContext.getString(
            R.string.result, result
        )

        viewModelScope.launch {
            writeToDatabase(result = result)
        }

        return true
    }

    /**
     * Checks if the input is valid or not.
     *
     * @return true if input is valid,
     * otherwise false.
     */
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
        val entry = ResultItem(
            date = Date(timeMillis),
            toolDiameter = diameter,
            spiralAngle = spiralAngle,
            cuttingDepth = cuttingHeight,
            cuttingWidth = cuttingWidth,
            fluteCount = fluteCount,
            flutePosition = flutePosition,
            result = result,
            type = EntityType.TYPE_SPIRAL_CONTACT
        )

        withContext(Dispatchers.IO) {
            database.insertEntry(entry)
        }
    }
}