package com.kou.promilling.results

import androidx.lifecycle.ViewModel
import com.kou.promilling.database.MillingDao

class ResultsViewModel(
    database: MillingDao
) : ViewModel() {
    val results = database.getSpiralContactEntries()
}