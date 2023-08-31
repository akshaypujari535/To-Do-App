package com.example.dailypad

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database (entities = [Note::class], version = 1)
@TypeConverters(converters::class)
abstract class toDoDatabase: RoomDatabase() {

    abstract fun notDao():noteDao

     companion object{
         @Volatile
        private var INSTANCE:toDoDatabase? = null

        fun getdatabase(context: Context):toDoDatabase{
            if (INSTANCE==null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        toDoDatabase::class.java,
                        "todoDB"
                    ).build()
                }
                }
            return INSTANCE!!
        }
    }
}