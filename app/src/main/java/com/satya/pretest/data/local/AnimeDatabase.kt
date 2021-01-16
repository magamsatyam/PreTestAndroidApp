package com.example.satya.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.satya.pretest.internal.DATABASE_NAME

@Database(entities = [DataEntity::class], version = 1)
abstract class AnimeDatabase: RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var instance: AnimeDatabase? = null

        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AnimeDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }
}