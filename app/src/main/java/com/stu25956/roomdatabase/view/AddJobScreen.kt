package com.stu25956.roomdatabase.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DefaultLocale")
@Composable
fun AddJobScreen(
    state: JobState,
    navController: NavController,
    onEvent: (JobsEvent) -> Unit
) {
    // State variables for date pickers
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // DatePickerDialog for "Date From"
    val dateFromPicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                state.dateFrom.value = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // DatePickerDialog for "Date To"
    val dateToPicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                state.dateTo.value = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
    // Scaffold with top bar and floating action button
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                title = {
                    Text("Back",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            modifier = Modifier.size(45.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        // Floating action button to save job
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(JobsEvent.SaveJob(
                        customerName = state.customerName.value,
                        jobDescription = state.jobDescription.value,
                        location = state.location.value,
                        jobType = state.jobType.value,
                        dateFrom = state.dateFrom.value,
                        dateTo = state.dateTo.value
                    ))
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Job"
                )
            }
        }
    ) {
        // Column in a scrollable modifier
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = "Add New Job",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Customer Name
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), // Reduced padding for better visibility
                value = state.customerName.value,
                onValueChange = { state.customerName.value = it },
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
                placeholder = { Text(text = "Customer Name") }
            )

            // Job Description
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.jobDescription.value,
                onValueChange = { state.jobDescription.value = it },
                placeholder = { Text(text = "Job Description") }
            )

            // Job Type
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.jobType.value,
                onValueChange = { state.jobType.value = it },
                placeholder = { Text(text = "Job Type") }
            )

            // Location
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.location.value,
                onValueChange = { state.location.value = it },
                placeholder = { Text(text = "Location") }
            )

            // Date From
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Date From: ${state.dateFrom.value}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { dateFromPicker.show() }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "Select Date",
                    )
                }
            }

            // Date To
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Date To: ${state.dateTo.value}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f) // Let text take available space
                )

                IconButton(
                    onClick = { dateToPicker.show() }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "Select Date",
                    )
                }
            }
        }
    }
}