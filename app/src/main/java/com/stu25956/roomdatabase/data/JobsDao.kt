package com.stu25956.roomdatabase.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JobsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertJobs(job: Jobs)

    @Delete
    suspend fun deleteJobs(jobs: Jobs)

    @Query("SELECT * FROM jobs ORDER BY dateTo")
    fun getJobsOrderByDate(): Flow<List<Jobs>>

    @Query("SELECT * FROM jobs ORDER BY CustomerName ASC")
    fun getJobsOrderByName(): Flow<List<Jobs>>
}