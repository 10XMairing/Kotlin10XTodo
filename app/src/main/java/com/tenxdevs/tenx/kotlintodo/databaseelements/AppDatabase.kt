package com.tenxdevs.tenx.kotlintodo.databaseelements

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Task::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDao


    companion object {
        private var INSTANCE : AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : AppDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "appdatabase.db").fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}