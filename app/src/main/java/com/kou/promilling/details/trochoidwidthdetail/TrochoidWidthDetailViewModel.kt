package com.kou.promilling.details.trochoidwidthdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kou.promilling.R
import com.kou.promilling.database.ResultItem

class TrochoidWidthDetailViewModel(
    item: ResultItem,
    app: Application
) : AndroidViewModel(app) {
    private val selectedItem = MutableLiveData<ResultItem>()

    init {
        selectedItem.value = item
    }

    val displayToolRadius: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_tool_radius, it.toolRadius
        )
    }

    val displayRoundingRadius: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_rounding_radius, it.curvatureRadius
        )
    }

    val displayTrochoidStep: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_trochoid_step, it.trochoidStep
        )
    }

    val displayResult: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_result, it.result
        )
    }

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