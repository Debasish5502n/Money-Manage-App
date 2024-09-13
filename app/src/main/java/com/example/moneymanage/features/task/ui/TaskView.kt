package com.example.moneymanage.features.task.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.data.local.entity.TaskModel
import com.example.moneymanage.features.navigation.MoneyManageRouteScreen
import com.example.moneymanage.features.navigation.TaskRouteScreen
import com.example.moneymanage.features.notes.repository.NoteRepository
import com.example.moneymanage.features.task.repository.TaskRepository
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary
import com.example.moneymanage.ui.theme.fontFamily2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskView(
    innerPadding: PaddingValues,
    navController: NavController
) {
    var markAs = rememberSaveable { mutableStateOf("inProgress") }

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier.padding(innerPadding),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(TaskRouteScreen.AddTask.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                containerColor = Color.White,
                contentColor = Primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "menu",
                    tint = Primary,
                    modifier = Modifier.background(Color.White),
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(0.2f))
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(bottom = 5.dp)
            ) {
                Text(
                    text = "Not Started",
                    color = if (markAs.value == "notStarted") Primary else Color.LightGray,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable {
                        if (markAs.value != "notStarted") {
                            markAs.value = "notStarted"
                        }
                    },
                    fontFamily = fontFamily2
                )
                Text(
                    text = "In Progress",
                    color = if (markAs.value == "inProgress") Primary else Color.LightGray,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable {
                        if (markAs.value != "inProgress") {
                            markAs.value = "inProgress"
                        }
                    },
                    fontFamily = fontFamily2
                )
                Text(
                    text = "Completed",
                    color = if (markAs.value == "completed") Primary else Color.LightGray,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable {
                        if (markAs.value != "completed") {
                            markAs.value = "completed"
                        }
                    },
                    fontFamily = fontFamily2
                )
            }

            Divider()


            TaskItem()

        }
    }
}

@Composable
fun TaskItem(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                clip = true,
                elevation = 1.dp
            )
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = "Hii sir good afternoon",
            color = Color.Black,
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.clickable {

            }
        )

        Text(
            text = "Hii sir good afternoon T",
            color = Color.Black,
            fontSize = 12.sp
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            Text(
                text = "Mark as - ",
                color = Color.Black,
                fontSize = 15.sp
            )

            Row(
                modifier = Modifier
                    .weight(1f),
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .border(
                            width = 1.dp,
                            color = Primary,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .background(
                            color = Primary.copy(0.1f)
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = "Progress",
                        color = Primary,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(
                            start = 10.dp,
                            top = 3.dp,
                            bottom = 3.dp
                        )
                    )
                    Text(
                        text = "▼",
                        color = Primary,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(
                            end = 5.dp,
                            start = 5.dp
                        )
                    )
                }

            }

            Text(
                text = "Dt - 20/03/2024",
                color = Color.Black.copy(0.7f),
                fontSize = 15.sp
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun TaskPreview() {
    MoneyManageTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()

            TaskView(
                innerPadding = innerPadding,
                navController = navController
            )
        }

    }
}

class FakeTaskRepository : TaskRepository {

    override suspend fun getAllTask(markAs: String): Flow<List<TaskModel>> {
        return flow {
            emit(
                listOf(
                    TaskModel(
                        id = 1,
                        date = 515,
                        title = "Hii",
                        description = "thpoj erhoni eronh",
                        markAs = "inProgress"
                    )
                )
            )
        }
    }

    override suspend fun insert(taskModel: TaskModel) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(taskModel: TaskModel) {
        TODO("Not yet implemented")
    }

    override suspend fun update(taskModel: TaskModel) {
        TODO("Not yet implemented")
    }

}