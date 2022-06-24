package com.kou.promilling.results

import androidx.lifecycle.*
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.ResultItem

class ResultsViewModel(
    database: MillingDao
) : ViewModel() {
    val spiral = database.getSpiralContactEntries()
    val cutting = database.getCuttingWidthEntries()
    val trochoid = database.getTrochoidWidthEntries()

    private val _navigateToItemDetail = MutableLiveData<ResultItem>()
    val navigateToItemDetail: LiveData<ResultItem>
        get() = _navigateToItemDetail

    fun displayItemDetail(item: ResultItem) {
        _navigateToItemDetail.value = item
    }

    fun displayItemDetailComplete() {
        _navigateToItemDetail.value = null
    }
}