package com.kou.promilling.calcs.cuttingwidthcalc

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.DatabaseCuttingWidth
import com.kou.promilling.database.MillingDao

class CuttingWidthViewModelFactory(
    private val dataSource: MillingDao,
    private val item: DatabaseCuttingWidth?,
    private val app: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CuttingWidthViewModel::class.java)) {
            return CuttingWidthViewModel(dataSource, item, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}