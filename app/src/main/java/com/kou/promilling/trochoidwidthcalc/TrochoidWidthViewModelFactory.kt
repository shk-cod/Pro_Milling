package com.kou.promilling.trochoidwidthcalc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.cuttingwidthcalc.CuttingWidthViewModel
import com.kou.promilling.database.MillingDao

class TrochoidWidthViewModelFactory(
    private val dataSource: MillingDao
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrochoidWidthViewModel::class.java)) {
            return TrochoidWidthViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}