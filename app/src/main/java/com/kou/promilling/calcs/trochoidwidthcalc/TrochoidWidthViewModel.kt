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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TrochoidWidthViewModel(
    private val database: MillingDao,
    item: ResultItem?,
    private val app: Application
): AndroidViewModel(app) {
    private var toolRadiusError: String? = null
    private var roundingRadiusError: String? = null
    private var trochoidStepError: String? = null

    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    private val _toolRadiusErrorFlow = MutableSharedFlow<String?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val toolRadiusErrorFlow: SharedFlow<String?>
        get() = _toolRadiusErrorFlow

    private val _roundingRadiusErrorFlow = MutableSharedFlow<String?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val roundingRadiusErrorFlow: SharedFlow<String?>
        get() = _roundingRadiusErrorFlow

    private val _trochoidStepErrorFlow = MutableSharedFlow<String?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val trochoidStepErrorFlow: SharedFlow<String?>
        get() = _trochoidStepErrorFlow


    //Two-way data binding
    val radius = MutableLiveData(Double.MIN_VALUE)
    val roundingRadius = MutableLiveData(Double.MIN_VALUE)
    val trochoidStep = MutableLiveData(Double.MIN_VALUE)

    init {
        item?.let { //if navigates from the details screen
            radius.value = it.toolRadius
            roundingRadius.value = it.curvatureRadius
            trochoidStep.value = it.trochoidStep
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

        val result = calculateTrochoidWidth(
            radius.value!!,
            roundingRadius.value!!,
            trochoidStep.value!!
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
            radius.value == Double.MIN_VALUE ||
            roundingRadius.value == Double.MIN_VALUE ||
            trochoidStep.value == Double.MIN_VALUE ||
            toolRadiusError != null ||
            roundingRadiusError != null ||
            trochoidStepError != null
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
            result = result,
            type = EntityType.TYPE_TROCHOID_WIDTH
        )

        withContext(Dispatchers.IO) {
            database.insertEntry(entry)
        }
    }

    fun checkToolRadius(toolRadius: Double, trochoidStep: Double) {
        when {
            toolRadius <= 0 -> {
                toolRadiusError = app.getString(R.string.error_tool_radius_zero)
                _toolRadiusErrorFlow.tryEmit(toolRadiusError)
            }
            (toolRadius * 2) < trochoidStep -> {
                toolRadiusError = app.getString(R.string.error_tool_radius_trochoid_step)
                _toolRadiusErrorFlow.tryEmit(toolRadiusError)
            }
            else -> {
                toolRadiusError = null
                _toolRadiusErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkRoundingRadius(roundingRadius: Double) {
        when {
            roundingRadius <= 0 -> {
                roundingRadiusError = app.getString(R.string.error_rounding_radius_zero)
                _roundingRadiusErrorFlow.tryEmit(roundingRadiusError)
            }
            else -> {
                roundingRadiusError = null
                _roundingRadiusErrorFlow.tryEmit(null)
            }
        }
    }

    fun checkTrochoidStep(trochoidStep: Double, toolRadius: Double) {
        when {
            trochoidStep <= 0 -> {
                trochoidStepError = app.getString(R.string.error_trochoid_step_zero)
                _trochoidStepErrorFlow.tryEmit(trochoidStepError)
            }
            trochoidStep > (toolRadius * 2) -> {
                trochoidStepError = app.getString(R.string.error_trochoid_step_tool_diameter)
                _trochoidStepErrorFlow.tryEmit(trochoidStepError)
            }
            else -> {
                trochoidStepError = null
                _trochoidStepErrorFlow.tryEmit(null)
            }
        }
    }
}