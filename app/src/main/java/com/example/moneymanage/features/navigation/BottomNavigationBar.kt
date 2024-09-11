package com.example.moneymanage.features.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.example.moneymanage.R
import com.example.moneymanage.ui.theme.Blue
import com.example.moneymanage.ui.theme.Primary
import com.example.moneymanage.ui.theme.fontFamily2
import com.example.moneymanage.utils.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    currentRoute: String,
    onClick: (NavigationItem) -> Unit
) {

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(60.dp)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    onClick(item)
                },
                label = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(0.dp) // Removes space between icon and label
                    ) {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                painter = if (currentRoute == item.route) {
                                    painterResource(id = item.selectedItem)
                                } else painterResource(id = item.unselectedItem),
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp) // Adjust size as needed
                            )
                        }
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            modifier = Modifier,
                            fontFamily = fontFamily2
                        )
                    }
                },
                icon = {

                },
                alwaysShowLabel = true, // Always show the label to avoid spacing issues
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White,
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}