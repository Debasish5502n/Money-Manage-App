package com.example.moneymanage.features.home.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.R
import com.example.moneymanage.graph.MainNavGraph
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.features.navigation.BottomNavigationBar
import com.example.moneymanage.features.navigation.DrawerNavBody
import com.example.moneymanage.features.navigation.DrawerNavHeader
import com.example.moneymanage.features.navigation.MainRouteScreen
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.fontFamily2
import com.example.moneymanage.utils.NavigationItem
import com.example.moneymanage.utils.bottomNavigationItemsList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardView(
    rootNavController: NavHostController
) {

    val items = listOf(
        NavigationItem(
            title = "Home",
            route = MainRouteScreen.Home.route,
            selectedItem = R.drawable.icon_home,
            unselectedItem = R.drawable.icon_home,
        ),
        NavigationItem(
            title = "Update Profile",
            route = MainRouteScreen.Profile.route,
            selectedItem = R.drawable.person,
            unselectedItem = R.drawable.person
        ),
        NavigationItem(
            title = "Day & Night",
            route = MainRouteScreen.Notification.route,
            selectedItem = R.drawable.icon_day_night,
            unselectedItem = R.drawable.icon_day_night,
            badgeCount = 26
        ),
        NavigationItem(
            title = "Settings",
            route = MainRouteScreen.Setting.route,
            selectedItem = R.drawable.icon_setting,
            unselectedItem = R.drawable.icon_setting,
        ),
        NavigationItem(
            title = "Rate us",
            route = "share",
            selectedItem = R.drawable.icon_star,
            unselectedItem = R.drawable.icon_star,
        ),
        NavigationItem(
            title = "Log out",
            route = "share",
            selectedItem = R.drawable.icon_logout,
            unselectedItem = R.drawable.icon_logout,
        )

    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRout = navBackStackEntry?.destination?.route

//    val topBarTitle: String =
//        if (currentRout != null) {
//            items[items.indexOfFirst {
//                it.route == currentRout
//            }].title
//        } else {
//            items[0].title
//        }

    val topBarTitle: String = currentRout.toString().replaceFirstChar { it.uppercaseChar() }

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet(
            modifier = Modifier
                .width(300.dp)
        ) {
            DrawerNavHeader()

            DrawerNavBody(
                items = items,
                currentRoute = currentRout
            ) { currentNavigationRout ->
                if (currentNavigationRout.route == "share") {
                    Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(currentNavigationRout.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                scope.launch {
                    drawerState.close()
                }
            }
        }
    }, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (topBarTitle == "Home") "Hii, Dear" else topBarTitle,
                            color = Color.Black,
                            fontFamily = fontFamily2
                        )
                    }
                }, navigationIcon = {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "menu",
                                tint = Color.Black
                            )
                        }
                    }
                },
                    actions = {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = "search",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                        .size(25.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.notification),
                                    contentDescription = "menu",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .size(25.dp)
                                )
                            }

                        }

                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.White),
                    modifier = Modifier.height(if (topBarTitle == "Home") 50.dp else 50.dp)
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    items = bottomNavigationItemsList,
                    currentRoute = currentRout.toString()
                ) { currentNavigationItem->
                    navController.navigate(currentNavigationItem.route){
                        navController.graph.startDestinationRoute?.let { startDestinationRout->
                            popUpTo(startDestinationRout){
                                saveState = true
                            }

                        }

                        launchSingleTop = true
                        restoreState = true
                    }

                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .background(Color.White)
            ) {
                MainNavGraph(
                    navController = navController,
                    rootNavController = rootNavController,
                    innerPadding = innerPadding
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview545() {
    MoneyManageTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()
            DashboardView(
                rootNavController = navController
            )
        }

    }
}