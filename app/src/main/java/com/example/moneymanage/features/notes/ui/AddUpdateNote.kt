package com.example.moneymanage.features.notes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.ui.theme.Blue
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddUpdateNote(
    notesViewModel: NoteViewModel = hiltViewModel(),
    noteModel: NoteModel?,
    navController: NavController?
) {
    val context = LocalContext.current

    var title = rememberSaveable { mutableStateOf(
        if(noteModel == null){ "" } else { noteModel.title }
    ) }
    var desc = rememberSaveable { mutableStateOf(
        if(noteModel == null){ "" } else { noteModel.description }
    ) }

    val btnClicked = remember { mutableStateOf(true) }

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
                    text = if (noteModel == null){ "Add Note" } else { "Update Note" },
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
                    label = { Text("Enter Title") },
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

                TextField(
                    value = desc.value,
                    onValueChange = { newText -> desc.value = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp) // Set height for multi-line
                        .padding(top = 30.dp, start = 10.dp, end = 10.dp),
                    placeholder = { Text("Enter description") },
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
                    if (noteModel == null) {
                        Button(
                            onClick = {
                                btnClicked.value = false
                                notesViewModel.insert(
                                    context = context,
                                    NoteModel(
                                        date = System.currentTimeMillis(),
                                        title = title.value,
                                        description = desc.value,
                                        noteBgColor = ""
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
                                notesViewModel.update(
                                    context = context,
                                    NoteModel(
                                        id = noteModel.id,
                                        date = noteModel.date,
                                        title = title.value,
                                        description = desc.value,
                                        noteBgColor = ""
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

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddUpdateNotePreview() {
    MoneyManageTheme {
        val navController = rememberNavController()

        // Initialize the ViewModel with the repository or mocked data
        val noteViewModel = NoteViewModel(
            repository = FakeNotesRepository()
        )

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AddUpdateNote(
                notesViewModel = noteViewModel,
                noteModel = null,
                navController = navController
            )
        }
    }
}