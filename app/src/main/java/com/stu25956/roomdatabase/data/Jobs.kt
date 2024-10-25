package com.stu25956.roomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class Jobs(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customerName: String,
    val location: String,
    val jobDescription: String,
    val jobType: String,
    val dateFrom: String,
    val dateTo: String
)