package com.example.moneymanage.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.moneymanage.data.local.entity.TaskModel
import com.example.moneymanage.features.navigation.Graph
import com.example.moneymanage.features.navigation.NotesRouteScreen
import com.example.moneymanage.features.navigation.TaskRouteScreen
import com.example.moneymanage.features.task.ui.AddUpdateTaskView
import com.google.gson.Gson

fun NavGraphBuilder.TaskGraph(
    rootNavController: NavHostController
) {
    navigation(
        route = Graph.TaskGraph,
        startDestination = TaskRouteScreen.AddTask.route
    ) {
        composable(
            route = TaskRouteScreen.UpdateTask.route+"/{taskModel}",
            arguments = listOf(
                navArgument("taskModel"){
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) {
            val json = it.arguments?.getString("taskModel")
            val taskModel = Gson().fromJson(json, TaskModel::class.java)
            AddUpdateTaskView(
                taskModel = taskModel!!,
                navController = rootNavController
            )
        }
        composable(route = TaskRouteScreen.AddTask.route) {
            AddUpdateTaskView(
                taskModel = null,
                navController = rootNavController
            )
        }
    }
}