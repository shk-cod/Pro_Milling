package com.kou.promilling.details.trochoidwidthdetail

import android.app.Application
import androidx.lifecycle.*
import com.kou.promilling.R
import com.kou.promilling.database.DatabaseTrochoidWidth

class TrochoidWidthDetailViewModel(
    item: DatabaseTrochoidWidth,
    app: Application
) : AndroidViewModel(app) {
    private val selectedItem = MutableLiveData<DatabaseTrochoidWidth>()

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
}