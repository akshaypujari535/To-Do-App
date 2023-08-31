package com.example.dailypad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class todoViewModel(application: Application): AndroidViewModel(application) {
    private val repository: toDoRepository

    init {
        val noteDao=toDoDatabase.getdatabase(application).notDao()
        repository= toDoRepository(noteDao)
    }
    fun getalltodo():LiveData<List<Note>>{
        return repository.getalltodo()
    }
    fun insert(note: Note){
        viewModelScope.launch(Dispatchers.IO){
           repository.insert(note)
        }
    }
    fun update(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(note)
        }
    }
    fun delete(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(note)
        }
    }

}