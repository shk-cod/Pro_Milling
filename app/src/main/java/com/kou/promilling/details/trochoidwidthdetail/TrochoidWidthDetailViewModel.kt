package com.kou.promilling.details.trochoidwidthdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.kou.promilling.database.ResultItem

class TrochoidWidthDetailViewModel(
    item: ResultItem
) : ViewModel() {
    private val selectedItem = MutableLiveData<ResultItem>()

    init {
        selectedItem.value = item
    }

    val displayToolRadius: LiveData<Double> = selectedItem.map { it.toolRadius }
    val displayRoundingRadius: LiveData<Double> = selectedItem.map { it.curvatureRadius }
    val displayTrochoidStep: LiveData<Double> = selectedItem.map { it.trochoidStep }
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