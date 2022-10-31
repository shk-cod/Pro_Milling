package com.kou.promilling.di

import android.app.Application
import androidx.room.Room
import com.kou.promilling.database.MillingDao
import com.kou.promilling.database.MillingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): MillingDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            MillingDatabase::class.java,
            "milling_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMillingDao(database: MillingDatabase): MillingDao {
        return database.millingDao
    }
}