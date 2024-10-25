package com.stu25956.roomdatabase.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stu25956.roomdatabase.data.Jobs
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JobsScreen(
    state: JobState,
    navController: NavController,
    onEvent: (JobsEvent) -> Unit
) {
    // Scaffold with top bar and floating action button
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "JOB LIST",
                    modifier = Modifier.weight(1f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                IconButton(onClick = { onEvent(JobsEvent.SortJobs) }) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Sort Jobs",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        // Floating action button to add new job
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    state.jobDescription.value = ""
                    navController.navigate("AddJobScreen")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add New Job"
                )
            }
        }
    ) { paddingValues ->
        // LazyColumn to display list of jobs
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.jobs.size) { index ->
                JobsItem(
                    job = state.jobs[index],
                    onEvent = onEvent
                )
            }
        }
    }
}
// JobsItem composable function to display each job item
@Composable
fun JobsItem(
    job: Jobs,
    onEvent: (JobsEvent) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = job.customerName,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = Color.Blue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Description: ${job.jobDescription}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Update to display job type correctly
            Text(
                text = "Type: ${job.jobType}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Location: ${job.location}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Format dates correctly with safe parsing
            val startDate = parseDate(job.dateFrom)
            val endDate = parseDate(job.dateTo)
            Row {
                Text(
                    text = "Start Date: $startDate",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "End Date: $endDate",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                )
            }
        }

        IconButton(
            onClick = { onEvent(JobsEvent.DeleteJob(job)) }
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Job",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

// Safe date parsing function
fun parseDate(dateString: String): String {
    if (dateString.isEmpty()) {
        return "N/A"
    }

    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString) ?: return "Invalid Date"
        date.toLocalDate()
    } catch (e: ParseException) {
        Log.e("DateParsing", "Failed to parse date: ${e.message}")
        "Invalid Date"
    }
}
// Extension function to convert Date to LocalDate
fun Date.toLocalDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}