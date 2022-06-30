package com.kou.promilling.details.spiralcontactdetail

import android.app.Application
import androidx.lifecycle.*
import com.kou.promilling.R
import com.kou.promilling.database.DatabaseSpiralContactLength

class SpiralContactDetailViewModel(
    item: DatabaseSpiralContactLength,
    app: Application
) : AndroidViewModel(app) {
    private val selectedItem = MutableLiveData<DatabaseSpiralContactLength>()

    init {
        selectedItem.value = item
    }

    val displayToolDiameter: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_tool_diameter, it.toolDiameter
        )
    }

    val displaySpiralAngle: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_spiral_angle, it.spiralAngle
        )
    }

    val displayCuttingHeight: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_cutting_height, it.cuttingDepth
        )
    }

    val displayCuttingWidth: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_cutting_width, it.cuttingWidth
        )
    }

    val displayFluteCount: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_flute_count, it.fluteCount
        )
    }

    val displayFLutePosition: LiveData<String> = Transformations.map(selectedItem) {
        app.applicationContext.getString(
            R.string.display_flute_position, it.flutePosition
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

    private val _navigateToCalc = MutableLiveData<DatabaseSpiralContactLength>()
    val navigateToCalc: LiveData<DatabaseSpiralContactLength>
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