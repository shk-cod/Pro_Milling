package com.kou.promilling.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    DatabaseSpiralContactLength::class,
    DatabaseCuttingWidth::class,
    DatabaseTrochoidWidth::class
], version = 1, exportSchema = false)
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
            ).build()
        }
        return INSTANCE
    }
}