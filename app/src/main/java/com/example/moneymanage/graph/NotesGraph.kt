package com.example.moneymanage.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.features.home.ui.AddUpdateMoneyScreen
import com.example.moneymanage.features.navigation.Graph
import com.example.moneymanage.features.navigation.NotesRouteScreen
import com.example.moneymanage.features.notes.ui.AddUpdateNote
import com.google.gson.Gson

fun NavGraphBuilder.NotesGraph(
    rootNavController: NavHostController
) {
    navigation(
        route = Graph.NotesGraph,
        startDestination = NotesRouteScreen.UpdateNote.route
    ) {
        composable(
            route = NotesRouteScreen.UpdateNote.route+"/{noteModel}",
            arguments = listOf(
                navArgument("noteModel"){
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) {
            val json = it.arguments?.getString("noteModel")
            val noteModel = Gson().fromJson(json, NoteModel::class.java)
            AddUpdateNote(
                noteModel = noteModel!!,
                navController = rootNavController
            )
        }
        composable(route = NotesRouteScreen.AddNote.route) {
            AddUpdateNote(
                noteModel = null,
                navController = rootNavController
            )
        }
    }
}