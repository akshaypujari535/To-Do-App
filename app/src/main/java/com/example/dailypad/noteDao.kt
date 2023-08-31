package com.example.dailypad

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface noteDao {

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
   suspend fun update(note: Note)

    @Query("SELECT * from dailyPad")
    fun getalltodo(): LiveData<List<Note>>

}