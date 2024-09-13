package com.example.moneymanage.features.task.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.R
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.data.local.entity.TaskModel
import com.example.moneymanage.features.task.viewModel.TaskViewModel
import com.example.moneymanage.ui.theme.Blue
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary
import com.example.moneymanage.utils.DateModifier
import com.example.moneymanage.utils.DateModifier.TimePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddUpdateTaskView(
    taskViewModel: TaskViewModel = hiltViewModel(),
    taskModel: TaskModel?,
    navController: NavController?
) {
    val context = LocalContext.current

    var title = rememberSaveable { mutableStateOf(
        taskModel?.title ?: ""
    ) }
    var desc = rememberSaveable { mutableStateOf(
        if(taskModel == null){ "" } else { taskModel.description }
    ) }

    var dateText = rememberSaveable { mutableStateOf(
        DateModifier.convertOnlyDate(
            taskModel?.date ?: System.currentTimeMillis()
    )) }
    var timeText = rememberSaveable { mutableStateOf(DateModifier.convertOnlyTime(
        taskModel?.date ?: System.currentTimeMillis()
    )) }
    var dateWithTimeText = rememberSaveable {
        mutableStateOf(
            taskModel?.date?.toString() ?: System.currentTimeMillis().toString()
        )
    }

    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    val showMarkAsOption = remember { mutableStateOf(false) }
    val btnClicked = remember { mutableStateOf(true) }

    val options = listOf("Not Started", "In Progress", "Completed")
    var markAs = remember { mutableStateOf(
        taskModel?.markAs ?: options[1]
    ) }

    val timePickerState = rememberTimePickerState(0, 0, true)

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = {
                    navController?.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = Color.Black
                    )
                }

                Text(
                    text = if (taskModel == null){ "Add Task" } else { "Update Task" },
                    fontSize = 20.sp,
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                //Title EditText
                TextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Task Title") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.LightGray.copy(0.5f),
                        cursorColor = Primary,
                        focusedIndicatorColor = Primary,
                        unfocusedIndicatorColor = Primary,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                //Title EditText

                //Date EditText
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 10.dp)
                ){
                    Text(
                        text = "Date: ",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier.weight(4f)
                    ){
                        Row {
                            Text(
                                text = dateText.value,
                                color = Color.Black,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .clickable { showDatePicker.value = true }
                            )
                            Text(
                                text = timeText.value,
                                color = Color.Black,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .clickable { showTimePicker.value = true }
                            )
                        }
                        Divider(
                            color = Color.Gray, // Line color
                            thickness = 1.dp,    // Line thickness
                            modifier = Modifier
                                .fillMaxWidth() // Makes the divider span the width of the column
                                .padding(top = 8.dp) // Adjust the space between text field and divider
                        )
                    }

                }
                //Date EditText

                //Mark As EditText
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 10.dp)
                ){
                    Text(
                        text = "Mark as: ",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier.weight(4f)
                    ){
                        Row(
                            modifier = Modifier
                                .clickable { showMarkAsOption.value = true }
                        ) {
                            Text(
                                text = markAs.value,
                                color = Color.Black,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_drop_down),
                                contentDescription = "search",
                                tint = Color.Black,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .size(25.dp)
                            )

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
                                            })
                                }

                            }

                        }
                        Divider(
                            color = Color.Gray, // Line color
                            thickness = 1.dp,    // Line thickness
                            modifier = Modifier
                                .fillMaxWidth() // Makes the divider span the width of the column
                                .padding(top = 8.dp) // Adjust the space between text field and divider
                        )
                    }

                }
                //Mark As EditText

                TextField(
                    value = desc.value,
                    onValueChange = { newText -> desc.value = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp) // Set height for multi-line
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    placeholder = { Text("Task description") },
                    singleLine = false, // Ensure it's not limited to a single line
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.LightGray.copy(0.5f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default)
                )
            }

            //Submit Btn
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
            ) {

                Button(
                    onClick = { navController?.popBackStack() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Gray),
                    modifier = Modifier
                        .padding(end = 5.dp)
                ) {
                    Text("Cancel")
                }

                if (btnClicked.value) {
                    if (taskModel == null) {
                        Button(
                            onClick = {
                                btnClicked.value = false
                                taskViewModel.insert(
                                    context = context,
                                    TaskModel(
                                        id = dateWithTimeText.value.toLong(),
                                        date = dateWithTimeText.value.toLong(),
                                        title = title.value,
                                        description = desc.value,
                                        markAs = markAs.value
                                    ),
                                    onInsert = {
                                        navController?.navigateUp()
                                    })

                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Primary
                            )
                        ) {
                            Text(
                                text = "Add",
                                color = Color.White
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                btnClicked.value = false
                                taskViewModel.update(
                                    context = context,
                                    TaskModel(
                                        id = taskModel.id,
                                        date = dateWithTimeText.value.toLong(),
                                        title = title.value,
                                        description = desc.value,
                                        markAs = markAs.value
                                    ),
                                    onUpdate = {
                                        navController?.navigateUp()
                                    })

                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Primary
                            )
                        ) {
                            Text(
                                text = "Update",
                                color = Color.White
                            )
                        }
                    }
                }

            }
            //Submit Btn

            if (showDatePicker.value){
                DateModifier.DatePickerModal(
                    onDateSelected = { date ->
                        var dateText1 = DateModifier.convertOnlyDate(date!!)
                        dateWithTimeText.value = DateModifier.convertDateToLong(dateText1).toString()

                        dateText.value = DateModifier.convertOnlyDate(dateWithTimeText.value.toLong())
                        timeText.value = "00:00"
                        showDatePicker.value = false
                    },
                    onDismiss = {
                        showDatePicker.value = false
                    }
                )
            }

            if (showTimePicker.value){
                TimePickerDialog(
                    onDismiss = {
                        showTimePicker.value = false
                    },
                    onConfirm = {
                        timeText.value = (timePickerState.hour + timePickerState.minute).toString()
                        showTimePicker.value = false
                        var timeInLong = timePickerState.hour * 60 * 60 * 1000
                        var minInLong = timePickerState.minute * 60 * 1000

                        dateWithTimeText.value = (dateWithTimeText.value.toLong() + timeInLong + minInLong).toString()

                        timeText.value = DateModifier.convertOnlyTime(
                            dateWithTimeText.value.toLong()
                        )
                    }
                ) {
                    TimePicker(
                        state = timePickerState,
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddUpdateNotePreview() {
    MoneyManageTheme {
        val navController = rememberNavController()

        // Initialize the ViewModel with the repository or mocked data
        val taskViewModel = TaskViewModel(
            repository = FakeTaskRepository()
        )

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AddUpdateTaskView(
                taskViewModel = taskViewModel,
                taskModel = null,
                navController = navController
            )
        }
    }
}