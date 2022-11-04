package com.kou.promilling.details.spiralcontactdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kou.promilling.database.ResultItem

class SpiralContactDetailViewModel(
    item: ResultItem
) : ViewModel() {
    private val selectedItem = MutableLiveData<ResultItem>()

    init {
        selectedItem.value = item
    }

    val displayToolDiameter: LiveData<Double> = Transformations.map(selectedItem) { it.toolDiameter }
    val displaySpiralAngle: LiveData<Double> = Transformations.map(selectedItem) { it.spiralAngle }
    val displayCuttingHeight: LiveData<Double> = Transformations.map(selectedItem) { it.cuttingDepth }
    val displayCuttingWidth: LiveData<Double> = Transformations.map(selectedItem) { it.cuttingWidth }
    val displayFluteCount: LiveData<Int> = Transformations.map(selectedItem) { it.fluteCount }
    val displayFLutePosition: LiveData<Double> = Transformations.map(selectedItem) { it.flutePosition }
    val displayResult: LiveData<Double> = Transformations.map(selectedItem) { it.result }

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