package com.kou.promilling.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [
    ResultItem::class
], version = 1, exportSchema = false)
@TypeConverters(DatabaseTypeConverters::class)
abstract class MillingDatabase: RoomDatabase() {
    abstract val millingDao: MillingDao
}