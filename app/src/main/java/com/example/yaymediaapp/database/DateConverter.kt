package com.example.yaymediaapp.database

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromZonedDateTime(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromLong(value: Long): Date {
        return Date(value)
    }
}
