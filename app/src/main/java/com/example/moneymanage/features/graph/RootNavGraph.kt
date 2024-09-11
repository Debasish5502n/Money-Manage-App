package com.example.moneymanage.features.graph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.features.home.ui.DashboardView
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.features.navigation.Graph

@Composable
fun RootNavGraph() {
    val rootNavController: NavHostController = rememberNavController()
    val moneyManageViewModel: MoneyManageViewModel = viewModel()
    NavHost(
        navController = rootNavController,
        route = Graph.RootGraph,
        startDestination = Graph.MainScreenGraph
    ) {
        composable(route = Graph.MainScreenGraph){
            DashboardView(
                rootNavController = rootNavController
            )
        }
        MoneyManageGraph(
            moneyManageViewModel = moneyManageViewModel,
            rootNavController = rootNavController
        )
        NotesGraph(
            rootNavController = rootNavController
        )
        //settingNavGraph(rootNavController = rootNavController)
    }
}