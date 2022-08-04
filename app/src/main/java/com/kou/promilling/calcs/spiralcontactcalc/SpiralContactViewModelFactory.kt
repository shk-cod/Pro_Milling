package com.kou.promilling.calcs.spiralcontactcalc

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.DatabaseSpiralContactLength
import com.kou.promilling.database.MillingDao

class SpiralContactViewModelFactory(
    private val dataSource: MillingDao,
    private val item: DatabaseSpiralContactLength?,
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