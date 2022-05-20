package com.kou.promilling.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem
import kotlinx.coroutines.flow.combine

class ResultsViewModel(
    database: MillingDao
) : ViewModel() {
    val spiral = database.getSpiralContactEntries()
    val cutting = database.getCuttingWidthEntries()
    val trochoid = database.getTrochoidWidthEntries()

}