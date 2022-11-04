package com.kou.promilling.calcs.cuttingwidthcalc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem

class CuttingWidthViewModelFactory(
    private val dataSource: MillingDao,
    private val item: ResultItem?
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CuttingWidthViewModel::class.java)) {
            return CuttingWidthViewModel(dataSource, item) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}