package com.example.moneymanage.utils

import com.example.moneymanage.R
import com.example.moneymanage.features.navigation.MainRouteScreen

val bottomNavigationItemsList = listOf(
    NavigationItem(
        title = "Home",
        route = MainRouteScreen.Home.route,
        selectedItem = R.drawable.icon_home,
        unselectedItem = R.drawable.icon_home,
    ),
    NavigationItem(
        title = "Notes",
        route = MainRouteScreen.Notes.route,
        selectedItem = R.drawable.icon_note,
        unselectedItem = R.drawable.icon_note
    ),
    NavigationItem(
        title = "Todo",
        route = MainRouteScreen.Task.route,
        selectedItem = R.drawable.icon_todo,
        unselectedItem = R.drawable.icon_todo
    ),
    NavigationItem(
        title = "Setting",
        route = MainRouteScreen.Setting.route,
        selectedItem = R.drawable.icon_friend,
        unselectedItem = R.drawable.icon_friend
    ),

)