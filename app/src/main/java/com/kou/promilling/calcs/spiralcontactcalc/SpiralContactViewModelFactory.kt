package com.kou.promilling.calcs.spiralcontactcalc

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem

class SpiralContactViewModelFactory(
    private val dataSource: MillingDao,
    private val item: ResultItem?,
    private val app: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpiralContactViewModel::class.java)) {
            return SpiralContactViewModel(dataSource, item, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}