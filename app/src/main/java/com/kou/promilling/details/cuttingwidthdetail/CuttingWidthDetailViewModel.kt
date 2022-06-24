package com.kou.promilling.details.cuttingwidthdetail

import android.app.Application
import androidx.lifecycle.*
import com.kou.promilling.R
import com.kou.promilling.database.DatabaseCuttingWidth

class CuttingWidthDetailViewModel(
    item: DatabaseCuttingWidth,
    app: Application
): AndroidViewModel(app) {
    private val selectedItem = MutableLiveData<DatabaseCuttingWidth>()

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
}