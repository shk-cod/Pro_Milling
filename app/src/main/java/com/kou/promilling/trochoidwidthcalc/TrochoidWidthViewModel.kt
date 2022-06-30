package com.kou.promilling.trochoidwidthcalc

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.promilling.calculateTrochoidWidth
import com.kou.promilling.database.DatabaseTrochoidWidth
import com.kou.promilling.database.MillingDao
import com.kou.promilling.formatResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TrochoidWidthViewModel(
    private val database: MillingDao,
    item: DatabaseTrochoidWidth?
): ViewModel() {
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

    @Suppress("UNUSED_PARAMETER")
    fun result(v: View) {
        if (!checkInput()) return

        val radius = radius.value!!
        val roundingRadius = roundingRadius.value!!
        val trochoidStep = trochoidStep.value!!
        val result = calculateTrochoidWidth(
            radius,
            roundingRadius,
            trochoidStep
        )

        _result.value = result.formatResult()

        viewModelScope.launch {
            writeToDatabase(result = result)
        }
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
        val entry = DatabaseTrochoidWidth(
            date = Date(timeMillis),
            toolRadius = radius,
            curvatureRadius = roundingRadius,
            trochoidStep = trochoidStep,
            result = result
        )
        withContext(Dispatchers.IO) {
            database.insertEntry(entry)
        }
    }
}