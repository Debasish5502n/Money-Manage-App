package com.example.moneymanage.features.task.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.R
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.data.local.entity.TaskModel
import com.example.moneymanage.features.home.ui.MoneyManageItem
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.features.navigation.MoneyManageRouteScreen
import com.example.moneymanage.features.navigation.TaskRouteScreen
import com.example.moneymanage.features.notes.repository.NoteRepository
import com.example.moneymanage.features.task.repository.TaskRepository
import com.example.moneymanage.features.task.viewModel.TaskViewModel
import com.example.moneymanage.ui.theme.Green
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary
import com.example.moneymanage.ui.theme.fontFamily2
import com.example.moneymanage.utils.DateModifier
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskView(
    taskViewModel: TaskViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current
    var markAs = rememberSaveable { mutableStateOf("inProgress") }

    val totalTasksList =
        taskViewModel.allTasks.collectAsStateWithLifecycle(initialValue = emptyList())

    fun loadTasks(){
        if (markAs.value == "notStarted") {
            taskViewModel.getTasks("Not Started")
        }else if (markAs.value == "inProgress") {
            taskViewModel.getTasks("In Progress")
        }else {
            taskViewModel.getTasks("Completed")
        }
    }

    LaunchedEffect(Unit) {
       loadTasks()
    }

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier.padding(innerPadding),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(TaskRouteScreen.AddTask.route) {
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
                            taskViewModel.getTasks("Not Started")
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
                            taskViewModel.getTasks("In Progress")
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
                            taskViewModel.getTasks("Completed")
                        }
                    },
                    fontFamily = fontFamily2
                )
            }

            Divider()

            ///Empty Money List
            if (totalTasksList.value.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_todo),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                            .size(40.dp),
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                    Text(
                        text = "Task Not Found",
                        color = Color.LightGray,
                        fontSize = 20.sp,
                        modifier = Modifier.clickable { }
                    )
                }

            }
            ///Empty Money List

            ///List of tasks
            LazyColumn{
                itemsIndexed(
                    items = totalTasksList.value,
                    key = { _, listItem ->
                        listItem.hashCode()
                    }
                ) { index, item ->

                    val coroutineScope = rememberCoroutineScope()
                    val state = rememberSwipeToDismissBoxState(
                        confirmValueChange = { state ->
                            if (state == SwipeToDismissBoxValue.EndToStart) {
                                coroutineScope.launch {
                                    taskViewModel.delete(item)
                                }
                                true
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(0.1f)
                                else -> {
                                    Color.Transparent
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 5.dp)
                                    .background(color = color as Color)
                                    .padding(horizontal = 20.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                ) {
                                    Text(
                                        text = "",
                                        modifier = Modifier.padding(end = 10.dp)
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "delete",
                                        tint = Color.Red
                                    )
                                }

                            }
                        },
                        dismissContent = {
                            TaskItem(
                                taskModel = item,
                                modifier = Modifier
                                    .clickable {
                                        val json = Gson().toJson(item)
                                        navController.navigate(TaskRouteScreen.UpdateTask.route+ "/$json") {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                onUpdateMarkAs = { updatedMarkAs ->
                                    taskViewModel.update(
                                        context = context,
                                        TaskModel(
                                            id = item.id,
                                            date = item.date,
                                            title = item.title,
                                            description = item.description,
                                            markAs = updatedMarkAs
                                        ),
                                        onUpdate = {
                                           loadTasks()
                                        }
                                    )
                                }
                            )
                        })

                }


            }
            ///List of tasks

        }
    }
}

@Composable
fun TaskItem(
    taskModel: TaskModel,
    modifier: Modifier,
    onUpdateMarkAs: (String) -> Unit
){
    val showMarkAsOption = remember { mutableStateOf(false) }

    val options = listOf("Not Started", "In Progress", "Completed")
    var markAs = remember { mutableStateOf(taskModel.markAs) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .padding(start = 5.dp)
            .padding(end = 5.dp)
            .padding(bottom = 1.dp)
            .shadow(
                shape = RoundedCornerShape(10.dp),
                clip = true,
                elevation = 2.dp
            )
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = taskModel.title,
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
            text = taskModel.description,
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
                    .weight(1f)
                    .padding(start = 10.dp)
                    .clickable {
                        showMarkAsOption.value = true
                    },
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .border(
                            width = 1.dp,
                            color =
                            if (taskModel.markAs == "In Progress") {
                                Primary
                            }else if (taskModel.markAs == "Completed"){
                                Green
                            }else{
                                Color.Gray
                            },
                            shape = RoundedCornerShape(5.dp)
                        )
                        .background(
                            color =
                            if (taskModel.markAs == "In Progress") {
                                Primary.copy(0.1f)
                            }else if (taskModel.markAs == "Completed"){
                                Green.copy(0.1f)
                            }else{
                                Color.Gray.copy(0.1f)
                            }
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = markAs.value,
                        color =
                        if (taskModel.markAs == "In Progress") {
                            Primary
                        }else if (taskModel.markAs == "Completed"){
                            Green
                        }else{
                            Color.Gray
                        },
                        fontSize = 10.sp,
                        modifier = Modifier.padding(
                            start = 10.dp,
                            top = 3.dp,
                            bottom = 3.dp
                        )
                    )
                    Text(
                        text = "▼",
                        color =
                        if (taskModel.markAs == "In Progress") {
                            Color.Gray
                        }else if (taskModel.markAs == "Completed"){
                            Color.Gray
                        }else{
                            Color.Gray
                        },
                        fontSize = 10.sp,
                        modifier = Modifier.padding(
                            end = 5.dp,
                            start = 5.dp
                        )
                    )
                }

                DropdownMenu(
                    expanded = showMarkAsOption.value,
                    onDismissRequest = {
                        showMarkAsOption.value = false
                    }
                ) {
                    options.forEach { mark ->
                        Text(mark,
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    markAs.value = mark
                                    showMarkAsOption.value = false
                                    onUpdateMarkAs(mark)
                                })
                    }

                }

            }

            Text(
                text = "Dt - ${DateModifier.convertTimeStampToDate(taskModel.date)}",
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

            // Initialize the ViewModel with the repository or mocked data
            val taskViewModel = TaskViewModel(
                repository = FakeTaskRepository()
            )

            TaskView(
                taskViewModel = taskViewModel,
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
                        markAs = "In Progress"
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