package com.stu25956.roomdatabase.view

import com.stu25956.roomdatabase.data.Jobs

sealed interface JobsEvent {
    data object SortJobs: JobsEvent

    data class DeleteJob(val jobs: Jobs): JobsEvent

    data class SaveJob(
        val customerName: String,
        val jobDescription: String,
        val jobType: String,
        val location: String,
        val dateFrom: String,
        val dateTo: String
    ): JobsEvent
}