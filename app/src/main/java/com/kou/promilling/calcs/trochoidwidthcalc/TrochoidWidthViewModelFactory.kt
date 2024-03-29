package com.kou.promilling.calcs.trochoidwidthcalc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem

class TrochoidWidthViewModelFactory(
    private val dataSource: MillingDao,
    private val item: ResultItem?
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrochoidWidthViewModel::class.java)) {
            return TrochoidWidthViewModel(dataSource, item) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}