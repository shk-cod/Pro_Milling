package com.kou.promilling.details.spiralcontactdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.kou.promilling.database.ResultItem

class SpiralContactDetailViewModel(
    item: ResultItem
) : ViewModel() {
    private val selectedItem = MutableLiveData<ResultItem>()

    init {
        selectedItem.value = item
    }

    val displayToolDiameter: LiveData<Double> = selectedItem.map { it.toolDiameter }
    val displaySpiralAngle: LiveData<Double> = selectedItem.map { it.spiralAngle }
    val displayCuttingHeight: LiveData<Double> = selectedItem.map { it.cuttingDepth }
    val displayCuttingWidth: LiveData<Double> = selectedItem.map { it.cuttingWidth }
    val displayFluteCount: LiveData<Int> = selectedItem.map { it.fluteCount }
    val displayFLutePosition: LiveData<Double> = selectedItem.map { it.flutePosition }
    val displayResult: LiveData<Double> = selectedItem.map { it.result }

    private val _navigateToResults = MutableLiveData(false)
    val navigateToResults: LiveData<Boolean>
        get() = _navigateToResults

    private val _navigateToCalc = MutableLiveData<ResultItem?>()
    val navigateToCalc: LiveData<ResultItem?>
        get() = _navigateToCalc

    /**
     * Called on "close" button click
     */
    fun onClose() {
        _navigateToResults.value = true
    }

    /**
     * Called on "reuse" button click
     */
    fun onReuse() {
        _navigateToCalc.value = selectedItem.value
    }

    fun doneNavigatingToResults() {
        _navigateToResults.value = false
    }

    fun doneNavigatingToCalc() {
        _navigateToCalc.value = null
    }
}