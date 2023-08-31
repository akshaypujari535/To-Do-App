package com.example.dailypad

import androidx.lifecycle.LiveData

class toDoRepository(private val noteDao: noteDao) {
    fun getalltodo():LiveData<List<Note>>{
        return noteDao.getalltodo()
    }
   suspend fun insert(note: Note){
       noteDao.insert(note)
   }
    suspend fun delete(note: Note){
        noteDao.delete(note)
    }
    suspend fun update(note: Note){
        noteDao.update(note)
    }

}