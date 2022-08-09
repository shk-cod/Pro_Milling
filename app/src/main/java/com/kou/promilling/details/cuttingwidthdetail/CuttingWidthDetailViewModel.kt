package com.kou.promilling.details.cuttingwidthdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kou.promilling.R
import com.kou.promilling.database.ResultItem

class CuttingWidthDetailViewModel(
    item: ResultItem,
    app: Application
): AndroidViewModel(app) {
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

    val displayCuttingWidth: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_cutting_width, it.cuttingWidth
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

    private val _navigateToCalc = MutableLiveData<ResultItem>()
    val navigateToCalc: LiveData<ResultItem>
        get() = _navigateToCalc

    fun onClose() {
        _navigateToResults.value = true
    }

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