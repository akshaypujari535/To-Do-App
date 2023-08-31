package com.example.dailypad

import androidx.room.TypeConverter
import java.util.Date

class converters {
@TypeConverter
fun fromtimestamp(value: Long?): Date?{
    return value?.let { Date(it) }
}
    @TypeConverter
    fun datetotimestamp(date: Date?):Long?{
     return date?.time
    }
}