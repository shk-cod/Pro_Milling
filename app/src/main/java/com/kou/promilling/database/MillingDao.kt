package com.kou.promilling.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MillingDao {

    /**
     * Gets all entries from the database in descending order.
     */
    @Query("select * from resultitem order by date desc")
    fun getEntries(): LiveData<List<ResultItem>>

    /**
     * Inserts one entry in database.
     */
    @Insert
    fun insertEntry(entry: ResultItem)

    /**
     * Deletes one entry from database.
     */
    @Delete
    fun deleteEntry(entry: ResultItem)
}