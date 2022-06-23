package com.kou.promilling.details.spiralcontactdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.DatabaseSpiralContactLength

class SpiralContactDetailViewModelFactory(
    private val item: DatabaseSpiralContactLength
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpiralContactDetailViewModel::class.java)) {
            return SpiralContactDetailViewModel(item) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}