package com.stu25956.roomdatabase.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stu25956.roomdatabase.data.Jobs
import com.stu25956.roomdatabase.data.JobsDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JobsViewModel(
    private val dao: JobsDao
) : ViewModel() {
    // State for the Jobs screen
    private val isSortedByDateAdded = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var jobs =
        isSortedByDateAdded.flatMapLatest { isSortedByDateAdded ->
            if (isSortedByDateAdded) {
                dao.getJobsOrderByDate()
            } else {
                dao.getJobsOrderByName()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(JobState())
    val state =
        combine(_state, isSortedByDateAdded, jobs) { state, isSortedByDateAdded, jobs ->
            state.copy(
                jobs = jobs
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), JobState())
    // Event for the Jobs screen
    fun getJobs(event: JobsEvent) {
        when (event) {
            is JobsEvent.SortJobs -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
            is JobsEvent.DeleteJob -> {
                viewModelScope.launch {
                    dao.deleteJobs(event.jobs)
                }
            }
            is JobsEvent.SaveJob -> {
                val job = Jobs(
                    customerName = state.value.customerName.value,
                    jobDescription = state.value.jobDescription.value,
                    location = state.value.location.value,
                    dateFrom = state.value.dateFrom.value,
                    dateTo = state.value.dateTo.value,
                    jobType = state.value.jobType.value
                )

                viewModelScope.launch {
                    dao.upsertJobs(job)
                }

                // Reset state fields after saving
                _state.update {
                    it.copy(
                        customerName = it.customerName.apply { value = "" },
                        jobDescription = it.jobDescription.apply { value = "" },
                        location = it.location.apply { value = "" },
                        dateFrom = it.dateFrom.apply { value = 0L.toString() },
                        dateTo = it.dateTo.apply { value = 0L.toString() },
                        jobType = it.jobType.apply { value = "" }
                    )
                }
            }
        }
    }
}