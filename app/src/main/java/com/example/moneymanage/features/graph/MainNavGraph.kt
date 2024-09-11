package com.example.moneymanage.features.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moneymanage.features.home.ui.HomeScreen
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.features.navigation.MainRouteScreen
import com.example.moneymanage.features.notes.ui.NoteViewModel
import com.example.moneymanage.features.notes.ui.NotesView

@Composable
fun MainNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    innerPadding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = MainRouteScreen.Home.route
    ) {
        composable(MainRouteScreen.Home.route) {
            HomeScreen(
                innerPadding = innerPadding,
                navController = rootNavController
            )
        }
        composable(MainRouteScreen.Notification.route) {
            //NotificationScreen(innerPadding = innerPadding)
        }
        composable(MainRouteScreen.Profile.route) {
            //NotificationScreen(innerPadding = innerPadding)
        }
        composable(MainRouteScreen.Notes.route) {
            NotesView(innerPadding = innerPadding)
        }
        composable(MainRouteScreen.Setting.route) {
            //SettingScreen(innerPadding = innerPadding)
        }
    }
}