package com.example.moneymanage.features.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.features.home.ui.AddUpdateMoneyScreen
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.features.navigation.Graph
import com.example.moneymanage.features.navigation.MoneyManageRouteScreen
import com.google.gson.Gson


fun NavGraphBuilder.MoneyManageGraph(
    moneyManageViewModel: MoneyManageViewModel,
    rootNavController: NavHostController
) {
    navigation(
        route = Graph.MoneyManageGraph,
        startDestination = MoneyManageRouteScreen.AddMoney.route
    ) {
        composable(
            route = MoneyManageRouteScreen.UpdateMoney.route+"/{moneyManageModel}",
            arguments = listOf(
                navArgument("moneyManageModel"){
                    type = NavType.StringType
                }
            )
        ) {
            val json = it.arguments?.getString("moneyManageModel")
            val moneyManageModel = Gson().fromJson(json, MoneyManageModel::class.java)
            AddUpdateMoneyScreen(
                moneyManageModel = moneyManageModel!!,
                navController = rootNavController
            )
        }
        composable(route = MoneyManageRouteScreen.AddMoney.route) {
            AddUpdateMoneyScreen(
                moneyManageModel = null,
                navController = rootNavController
            )
        }
    }
}