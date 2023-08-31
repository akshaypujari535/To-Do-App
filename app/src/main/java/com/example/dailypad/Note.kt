package com.example.dailypad

import androidx.databinding.adapters.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity (tableName = "dailyPad")
data class Note(
    @PrimaryKey (autoGenerate = true)
    val id:Long,
    val title:String,
    val desc:String,
    @TypeConverters(Converters::class)
    val createdDate: Date,
    @TypeConverters(Converters::class)
    val duedate: Date,
    val isCompleted:Boolean=false
)
