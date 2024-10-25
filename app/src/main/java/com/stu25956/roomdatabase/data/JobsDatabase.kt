package com.stu25956.roomdatabase.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Jobs::class],
    version = 1
)
abstract class JobsDatabase : RoomDatabase() {
    abstract fun jobDao(): JobsDao
}