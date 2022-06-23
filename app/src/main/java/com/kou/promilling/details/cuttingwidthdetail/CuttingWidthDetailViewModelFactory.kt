package com.kou.promilling.details.cuttingwidthdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.DatabaseCuttingWidth

class CuttingWidthDetailViewModelFactory(
    private val item: DatabaseCuttingWidth
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CuttingWidthDetailViewModel::class.java)) {
            return CuttingWidthDetailViewModel(item) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}