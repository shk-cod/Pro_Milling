package com.kou.promilling.details.cuttingwidthdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kou.promilling.database.ResultItem

class CuttingWidthDetailViewModel(
    item: ResultItem
): ViewModel() {
    private val selectedItem = MutableLiveData<ResultItem>()

    init {
        selectedItem.value = item
    }

    val displayToolRadius: LiveData<Double> = Transformations.map(selectedItem) { it.toolRadius }
    val displayRoundingRadius: LiveData<Double> = Transformations.map(selectedItem) { it.curvatureRadius }
    val displayCuttingWidth: LiveData<Double> = Transformations.map(selectedItem) { it.cuttingWidth }
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