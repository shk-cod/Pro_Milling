package com.kou.promilling.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MillingDao {
    @Query("select * from resultitem order by date desc")
    fun getEntries(): LiveData<List<ResultItem>>

    @Insert
    fun insertEntry(entry: ResultItem)

    @Delete
    fun deleteEntry(entry: ResultItem)
}