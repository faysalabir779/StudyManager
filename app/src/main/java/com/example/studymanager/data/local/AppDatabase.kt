package com.example.studymanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject
import com.example.studymanager.doamin.model.Task

@Database(
    entities = [Subject::class, Task::class, Session::class],
    version = 1
)

@TypeConverters(ColorListConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun subjectDao(): SubjectDao
    abstract fun taskDao(): TaskDao
    abstract fun sessionDao(): SessionDao
}