package com.kou.promilling.calcs.trochoidwidthcalc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kou.promilling.R
import com.kou.promilling.calculateTrochoidWidth
import com.kou.promilling.database.EntityType
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TrochoidWidthViewModel(
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
    val trochoidStep = MutableLiveData(Double.MIN_VALUE)

    init {
        item?.let {
            radius.value = it.toolRadius
            roundingRadius.value = it.curvatureRadius
            trochoidStep.value = it.trochoidStep
        }
    }

    fun result(): Boolean {
        if (!checkInput()) return false

        val result = calculateTrochoidWidth(
            radius.value!!,
            roundingRadius.value!!,
            trochoidStep.value!!
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
        if (
            radius.value == Double.MIN_VALUE ||
            roundingRadius.value == Double.MIN_VALUE ||
            trochoidStep.value == Double.MIN_VALUE
        ) return false

        return true
    }

    private suspend fun writeToDatabase(
        timeMillis: Long = System.currentTimeMillis(),
        radius: Double = this.radius.value!!,
        roundingRadius: Double = this.roundingRadius.value!!,
        trochoidStep: Double = this.trochoidStep.value!!,
        result: Double
    ) {
        val entry = ResultItem(
            date = Date(timeMillis),
            toolRadius = radius,
            curvatureRadius = roundingRadius,
            trochoidStep = trochoidStep,
            result = result
        ).apply { this.type = EntityType.TYPE_TROCHOID_WIDTH }
        withContext(Dispatchers.IO) {
            database.insertEntry(entry)
        }
    }
}