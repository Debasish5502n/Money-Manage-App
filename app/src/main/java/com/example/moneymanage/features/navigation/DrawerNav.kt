package com.example.moneymanage.features.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.R
import com.example.moneymanage.features.home.ui.FakeMoneyManageRepository
import com.example.moneymanage.features.home.ui.HomeScreen
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.ui.theme.Blue
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary
import com.example.moneymanage.ui.theme.fontFamily2
import com.example.moneymanage.utils.NavigationItem

@Composable
fun DrawerNavHeader() {
    Column(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color.White),
    verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_person), contentDescription = "Person",
            modifier = Modifier
                .padding(top = 10.dp, start = 20.dp)
                .clip(CircleShape)
                .size(80.dp)
                .background(Primary.copy(0.1f))
        )
        Text(
            text = "Hii, Dear",
            modifier = Modifier
                .padding(top = 5.dp, start = 20.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily2
            )
        )

        Row(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp, start = 20.dp)
        ) {
            Text(
                text = "25 Balance",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily2
                )
            )
            Text(
                text = "35 Task Completed",
                modifier = Modifier
                    .padding(start = 10.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily2
                )
            )
        }

        Divider()
    }
}

@Composable
fun DrawerNavBody(
    items: List<NavigationItem>,
    currentRoute: String?,
    onClick: (NavigationItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .padding(top = 10.dp)
    ) {
        items.forEachIndexed { index, navigationItem ->
            NavigationDrawerItem(label = {
                Text(text = navigationItem.title)
            }, selected = currentRoute == navigationItem.route, onClick = {
                onClick( navigationItem )
            }, icon = {
                Icon(
                    painter = if (currentRoute == navigationItem.route) {
                        painterResource(id = navigationItem.selectedItem)
                    } else painterResource(id = navigationItem.unselectedItem),
                    contentDescription = navigationItem.title
                )
            },
                badge = {
                    navigationItem.badgeCount?.let {
                        Text(text = it.toString())
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 2.dp)
                    .height(50.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Primary.copy(0.1f),
                    unselectedContainerColor = Color.White,
                    selectedIconColor = Primary,
                    selectedTextColor = Primary
                ),
                shape = MaterialTheme.shapes.small
            )
        }

        Divider(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 10.dp))

        Column(
            modifier = Modifier.padding(start = 40.dp, top = 20.dp)
        ) {
            Text(text = "Term & Condition")
            Text(
                text = "Privacy Policy",
                modifier = Modifier.padding(top = 10.dp)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview11() {
    MoneyManageTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()

            DrawerNavHeader()
        }

    }
}