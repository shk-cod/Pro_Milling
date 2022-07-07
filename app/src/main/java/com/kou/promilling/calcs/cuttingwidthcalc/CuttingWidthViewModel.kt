package com.kou.promilling.calcs.cuttingwidthcalc

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.promilling.calculateCuttingWidth
import com.kou.promilling.database.DatabaseCuttingWidth
import com.kou.promilling.database.MillingDao
import com.kou.promilling.formatResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CuttingWidthViewModel(
    private val database: MillingDao,
    item: DatabaseCuttingWidth?
): ViewModel() {
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

    @Suppress("UNUSED_PARAMETER")
    fun result(v: View) {
        if (!checkInput()) return

        val radius = radius.value!!
        val roundingRadius = roundingRadius.value!!
        val cuttingWidth = cuttingWidth.value!!
        val result = calculateCuttingWidth(
            radius,
            roundingRadius,
            cuttingWidth
        )

        //TODO: make ViewModel an AndroidViewModel and extract this to string resources
        _result.value = "${result.formatResult()} mm"

        viewModelScope.launch {
            writeToDatabase(result = result)
        }
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
        val entry = DatabaseCuttingWidth(
            date = Date(timeMillis),
            toolRadius = radius,
            curvatureRadius = roundingRadius,
            cuttingWidth = cuttingWidth,
            result = result
        )
        withContext(Dispatchers.IO) {
            database.insertEntry(entry)
        }
    }
}