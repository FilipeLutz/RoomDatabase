package com.stu25956.roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.stu25956.roomdatabase.data.JobsDatabase
import com.stu25956.roomdatabase.view.AddJobScreen
import com.stu25956.roomdatabase.view.JobsScreen
import com.stu25956.roomdatabase.view.JobsViewModel
import com.stu25956.roomdatabase.ui.theme.RoomDatabaseTheme

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            JobsDatabase::class.java,
            "jobs_database"
        ).build()
    }

    private val viewModel by viewModels<JobsViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return JobsViewModel(database.jobDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDatabaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "JobsScreen"
                    ) {
                        composable("JobsScreen") {
                            JobsScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::getJobs
                            )
                        }
                        composable("AddJobScreen") {
                            AddJobScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::getJobs
                            )
                        }
                    }
                }
            }
        }
    }
}