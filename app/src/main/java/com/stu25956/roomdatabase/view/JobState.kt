package com.stu25956.roomdatabase.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.stu25956.roomdatabase.data.Jobs

data class JobState(
    val jobs: List<Jobs> = listOf(),
    val customerName: MutableState<String> = mutableStateOf(""),
    val jobDescription: MutableState<String> = mutableStateOf(""),
    val location: MutableState<String> = mutableStateOf(""),
    val jobType: MutableState<String> = mutableStateOf(""),
    val dateFrom: MutableState<String> = mutableStateOf(""),
    val dateTo: MutableState<String> = mutableStateOf("")
)