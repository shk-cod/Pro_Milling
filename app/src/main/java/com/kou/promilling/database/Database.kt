package com.kou.promilling.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [
    ResultItem::class
], version = 1, exportSchema = false)
@TypeConverters(DatabaseTypeConverters::class)
abstract class MillingDatabase: RoomDatabase() {
    abstract val millingDao: MillingDao
}

private lateinit var INSTANCE: MillingDatabase

fun getDatabase(context: Context): MillingDatabase {
    synchronized(MillingDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MillingDatabase::class.java,
                "milling_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE
    }
}