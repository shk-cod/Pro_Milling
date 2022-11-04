@file:Suppress("unused")

package com.kou.promilling.calcs.spiralcontactcalc

import androidx.lifecycle.*
import com.kou.promilling.R
import com.kou.promilling.calculateSpiralContact
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

class SpiralContactViewModel(
    private val database: MillingDao,
    item: ResultItem?
): ViewModel() {
    private var toolDiameterError: Int? = null
    private var spiralAngleError: Int? = null
    private var cuttingHeightError: Int? = null
    private var cuttingWidthError: Int? = null
    private var fluteCountError: Int? = null
    private var flutePositionError: Int? = null

    private val _result = MutableLiveData<Double>()
    val result: LiveData<Double>
        get() = _result


    private val _toolDiameterErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val toolDiameterErrorFlow: SharedFlow<Int?>
        get() = _toolDiameterErrorFlow

    private val _spiralAngleErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val spiralAngleErrorFlow: SharedFlow<Int?>
        get() = _spiralAngleErrorFlow

    private val _cuttingHeightErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val cuttingHeightErrorFlow: SharedFlow<Int?>
        get() = _cuttingHeightErrorFlow

    private val _cuttingWidthErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val cuttingWidthErrorFlow: SharedFlow<Int?>
        get() = _cuttingWidthErrorFlow

    private val _fluteCountErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val fluteCountErrorFlow: SharedFlow<Int?>
        get() = _fluteCountErrorFlow

    private val _flutePositionErrorFlow = MutableSharedFlow<Int?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val flutePositionErrorFlow: SharedFlow<Int?>
        get() = _flutePositionErrorFlow

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
            diameter.value == Double.MIN_VALUE ||
            spiralAngle.value == Double.MIN_VALUE ||
            cuttingHeight.value == Double.MIN_VALUE ||
            cuttingWidth.value == Double.MIN_VALUE ||
            fluteCount.value == Int.MIN_VALUE ||
            flutePosition.value == Double.MIN_VALUE ||
            toolDiameterError != null ||
            spiralAngleError != null ||
            cuttingHeightError != null ||
            cuttingWidthError != null ||
            fluteCountError != null ||
            flutePositionError != null
        ) return false

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

    fun checkToolDiameter(toolDiameter: Double, cuttingWidth: Double) {
        when {
            toolDiameter <= 0 -> {
                toolDiameterError = R.string.error_tool_diameter_zero
                _toolDiameterErrorFlow.tryEmit(toolDiameterError)
            }
            toolDiameter < cuttingWidth -> {
                toolDiameterError = R.string.error_tool_diameter_cutting_width
                _toolDiameterErrorFlow.tryEmit(toolDiameterError)
            }
            else -> {
                toolDiameterError = null
                _toolDiameterErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkSpiralAngle(spiralAngle: Double) {
        when {
            (spiralAngle <= 0 || spiralAngle >= 90) -> {
                spiralAngleError = R.string.error_spiral_angle_zero
                _spiralAngleErrorFlow.tryEmit(spiralAngleError)
            }
            else -> {
                spiralAngleError = null
                _spiralAngleErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkCuttingHeight(cuttingHeight: Double) {
        when {
            cuttingHeight <= 0 -> {
                cuttingHeightError = R.string.error_cutting_height_zero
                _cuttingHeightErrorFlow.tryEmit(cuttingHeightError)
            }
            else -> {
                cuttingHeightError = null
                _cuttingHeightErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkCuttingWidth(cuttingWidth: Double, toolDiameter: Double) {
        when {
            cuttingWidth <= 0 -> {
                cuttingWidthError = R.string.error_cutting_width_zero
                _cuttingWidthErrorFlow.tryEmit(cuttingWidthError)
            }
            cuttingWidth > toolDiameter -> {
                cuttingWidthError = R.string.error_cutting_width_tool_radius
                _cuttingWidthErrorFlow.tryEmit(cuttingWidthError)
            }
            else -> {
                cuttingWidthError = null
                _cuttingWidthErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkFluteCount(fluteCount: Int) {
        when {
            (fluteCount <= 0 || fluteCount >= 300) -> {
                fluteCountError = R.string.error_flute_count_zero
                _fluteCountErrorFlow.tryEmit(fluteCountError)
            }
            else -> {
                fluteCountError = null
                _fluteCountErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkFlutePosition(flutePosition: Double) {
        when {
            (flutePosition < 0 || flutePosition >= 360) -> {
                flutePositionError = R.string.error_flute_position_zero
                _flutePositionErrorFlow.tryEmit(flutePositionError)
            }
            else -> {
                flutePositionError = null
                _flutePositionErrorFlow.tryEmit(null)
            }
        }
    }
}