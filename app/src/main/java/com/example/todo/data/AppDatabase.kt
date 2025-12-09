package com.example.todo.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.MyApp

@Database(entities = [Task::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    MyApp.appContext,
                    AppDatabase::class.java,
                    "tasks.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
