package com.kou.promilling.calcs.cuttingwidthcalc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kou.promilling.R
import com.kou.promilling.calculateCuttingWidth
import com.kou.promilling.database.EntityType
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CuttingWidthViewModel(
    private val database: MillingDao,
    item: ResultItem?,
    private val app: Application
): AndroidViewModel(app) {
    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    //Two-way data binding
    val radius = MutableLiveData(Double.MIN_VALUE)
    val roundingRadius = MutableLiveData(Double.MIN_VALUE)
    val cuttingWidth = MutableLiveData(Double.MIN_VALUE)

    init {
        item?.let {
            radius.value = it.toolRadius
            roundingRadius.value = it.curvatureRadius
            cuttingWidth.value = it.cuttingWidth
        }
    }

    fun result(): Boolean {
        if (!checkInput()) return false


        val result = calculateCuttingWidth(
            radius.value!!,
            roundingRadius.value!!,
            cuttingWidth.value!!
        )

        _result.value = app.applicationContext.getString(
            R.string.result, result
        )

        viewModelScope.launch {
            writeToDatabase(result = result)
        }

        return true
    }

    private fun checkInput(): Boolean {
        if (radius.value == Double.MIN_VALUE ||
            roundingRadius.value == Double.MIN_VALUE ||
            cuttingWidth.value == Double.MIN_VALUE
        ) return false

        return true
    }

    private suspend fun writeToDatabase(
        timeMillis: Long = System.currentTimeMillis(),
        radius: Double = this.radius.value!!,
        roundingRadius: Double = this.roundingRadius.value!!,
        cuttingWidth: Double = this.cuttingWidth.value!!,
        result: Double
    ) {
        val entry = ResultItem(
            date = Date(timeMillis),
            toolRadius = radius,
            curvatureRadius = roundingRadius,
            cuttingWidth = cuttingWidth,
            result = result
        ).apply { this.type = EntityType.TYPE_CUTTING_WIDTH }
        withContext(Dispatchers.IO) {
            database.insertEntry(entry)
        }
    }
}