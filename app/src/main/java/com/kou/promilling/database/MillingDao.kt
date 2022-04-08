package com.kou.promilling.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MillingDao {
    @Query("select * from databasespiralcontactlength")
    fun getSpiralContactEntries(): LiveData<List<DatabaseSpiralContactLength>>

    @Query("select * from databasecuttingwidth")
    fun getCuttingWidthEntries(): LiveData<List<DatabaseCuttingWidth>>

    @Query("select * from databasetrochoidwidth")
    fun getTrochoidWidthEntries(): LiveData<List<DatabaseTrochoidWidth>>

    @Insert
    fun insertEntry(entry: DatabaseSpiralContactLength)

    @Insert
    fun insertEntry(entry: DatabaseCuttingWidth)

    @Insert
    fun insertEntry(entry: DatabaseTrochoidWidth)

    @Delete
    fun deleteEntry(entry: DatabaseSpiralContactLength)

    @Delete
    fun deleteEntry(entry: DatabaseCuttingWidth)

    @Delete
    fun deleteEntry(entry: DatabaseTrochoidWidth)
}