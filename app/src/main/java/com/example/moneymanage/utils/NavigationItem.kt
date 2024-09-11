package com.example.moneymanage.utils

data class NavigationItem(
    val title: String,
    val route: String,
    val selectedItem: Int,
    val unselectedItem: Int,
    val badgeCount: Int? = null
)