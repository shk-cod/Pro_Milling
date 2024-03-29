package com.kou.promilling.details.trochoidwidthdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.ResultItem

class TrochoidWidthDetailViewModelFactory(
    private val item: ResultItem
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrochoidWidthDetailViewModel::class.java)) {
            return TrochoidWidthDetailViewModel(item) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }
}