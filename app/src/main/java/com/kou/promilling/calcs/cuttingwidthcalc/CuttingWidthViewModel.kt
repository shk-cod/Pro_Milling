package com.kou.promilling.calcs.cuttingwidthcalc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.promilling.R
import com.kou.promilling.calculateCuttingWidth
import com.kou.promilling.database.EntityType
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CuttingWidthViewModel(
    private val database: MillingDao,
    item: ResultItem?
) : ViewModel() {
    private var toolRadiusError: Int? = null
    private var roundingRadiusError: Int? = null
    private var cuttingWidthError: Int? = null

    private val _result = MutableLiveData<Double>()
    val result: LiveData<Double>
        get() = _result


    private val _toolRadiusErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val toolRadiusErrorFlow: SharedFlow<Int?>
        get() = _toolRadiusErrorFlow

    private val _roundingRadiusErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val roundingRadiusErrorFlow: SharedFlow<Int?>
        get() = _roundingRadiusErrorFlow

    private val _cuttingWidthErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val cuttingWidthErrorFlow: SharedFlow<Int?>
        get() = _cuttingWidthErrorFlow


    //Two-way data binding
    val radius = MutableLiveData(Double.MIN_VALUE)
    val roundingRadius = MutableLiveData(Double.MIN_VALUE)
    val cuttingWidth = MutableLiveData(Double.MIN_VALUE)

    init {
        item?.let { //if navigates from the details screen
            radius.value = it.toolRadius
            roundingRadius.value = it.curvatureRadius
            cuttingWidth.value = it.cuttingWidth
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


        val result = calculateCuttingWidth(
            radius.value!!,
            roundingRadius.value!!,
            cuttingWidth.value!!
        )

        //formatting the result
        _result.value = result

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
            radius.value == Double.MIN_VALUE ||
            roundingRadius.value == Double.MIN_VALUE ||
            cuttingWidth.value == Double.MIN_VALUE ||
            toolRadiusError != null ||
            roundingRadiusError != null ||
            cuttingWidthError != null
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
            result = result,
            type = EntityType.TYPE_CUTTING_WIDTH
        )

        withContext(Dispatchers.IO) {
            database.insertEntry(entry)
        }
    }

    fun checkToolRadius(toolRadius: Double, roundingRadius: Double, cuttingWidth: Double) {
        when {
            toolRadius <= 0 -> {
                toolRadiusError = R.string.error_tool_radius_zero
                _toolRadiusErrorFlow.tryEmit(toolRadiusError)
            }
            toolRadius > roundingRadius -> {
                toolRadiusError = R.string.error_tool_radius_rounding_radius
                _toolRadiusErrorFlow.tryEmit(toolRadiusError)
            }
            (toolRadius * 2) < cuttingWidth -> {
                toolRadiusError = R.string.error_tool_radius_cutting_width
                _toolRadiusErrorFlow.tryEmit(toolRadiusError)
            }
            else -> {
                toolRadiusError = null
                _toolRadiusErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkRoundingRadius(roundingRadius: Double, toolRadius: Double) {
        when {
            roundingRadius <= 0 -> {
                roundingRadiusError = R.string.error_rounding_radius_zero
                _roundingRadiusErrorFlow.tryEmit(roundingRadiusError)
            }
            roundingRadius < toolRadius -> {
                roundingRadiusError = R.string.error_rounding_radius_tool_radius
                _roundingRadiusErrorFlow.tryEmit(roundingRadiusError)
            }

            else -> {
                roundingRadiusError = null
                _roundingRadiusErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkCuttingWidth(cuttingWidth: Double, toolRadius: Double) {
        when {
            cuttingWidth <= 0 -> {
                cuttingWidthError = R.string.error_cutting_width_zero
                _cuttingWidthErrorFlow.tryEmit(cuttingWidthError)
            }
            cuttingWidth > (toolRadius * 2) -> {
                cuttingWidthError = R.string.error_cutting_width_tool_radius
                _cuttingWidthErrorFlow.tryEmit(cuttingWidthError)
            }
            else -> {
                cuttingWidthError = null
                _cuttingWidthErrorFlow.tryEmit(null)
            }
        }
    }
}