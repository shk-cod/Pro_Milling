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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

class CuttingWidthViewModel(
    private val database: MillingDao,
    item: ResultItem?,
    private val app: Application
) : AndroidViewModel(app) {
    private var roundingRadiusError: String? = null

    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    private val _roundingRadiusErrorFlow = MutableSharedFlow<String?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val roundingRadiusErrorFlow: SharedFlow<String?>
        get() = _roundingRadiusErrorFlow

    val cuttingWidthError: StateFlow<String?> = MutableStateFlow(null)

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
            cuttingWidth.value == Double.MIN_VALUE ||
            roundingRadiusError != null ||
            cuttingWidthError.value != null
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

    fun checkRoundingRadius(roundingRadius: Double, toolRadius: Double) {
        when {
            roundingRadius < toolRadius -> {
                Timber.i("$roundingRadius, $toolRadius emitted")
                roundingRadiusError = "Rounding radius cannot be smaller than tool radius"
                _roundingRadiusErrorFlow.tryEmit(roundingRadiusError)
            }

            else -> {
                roundingRadiusError = null
                _roundingRadiusErrorFlow.tryEmit(roundingRadiusError)
            }
        }
    }

    fun checkCuttingWidth(value: Double) {

    }
}