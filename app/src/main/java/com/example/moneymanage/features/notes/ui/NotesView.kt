package com.example.moneymanage.features.notes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.features.home.repository.MoneyManageRepository
import com.example.moneymanage.features.home.ui.FakeMoneyManageRepository
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.features.navigation.MoneyManageRouteScreen
import com.example.moneymanage.features.navigation.NotesRouteScreen
import com.example.moneymanage.features.notes.repository.NoteRepository
import com.example.moneymanage.ui.theme.Blue
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary
import com.example.moneymanage.ui.theme.fontFamily2
import com.example.moneymanage.utils.DateModifier
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun NotesView(
    notesViewModel: NoteViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current

    val notes = notesViewModel.notes.observeAsState(initial = emptyList())

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .padding(innerPadding)
            .background(color = Color.White),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NotesRouteScreen.AddNote.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    //showDialog.value = true
                },
                containerColor = Color.White,
                contentColor = Primary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "menu",
                    tint = Primary,
                    modifier = Modifier.background(Color.White),
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(0.1f))
        ) {
            Divider()
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
            ) {
                items(notes.value.size) { item ->
                    ItemRow(
                        item = notes.value[item],
                        onNoteDelete = { deleteNote ->
                            notesViewModel.delete(
                                noteModel = deleteNote
                            )
                        },
                        onNoteEdit = { note ->
                            val json = Gson().toJson(note)
                            navController.navigate(
                                NotesRouteScreen.UpdateNote.route+ "/$json"
                            ){
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Spacer for padding between items
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemRow(item: NoteModel,
            onNoteDelete: (NoteModel) -> Unit,
            onNoteEdit: (NoteModel) -> Unit) {

    val showDeleteConfirmDialog = rememberSaveable { mutableStateOf(false) }
    val randomColors = rememberSaveable {
        listOf(
            "#c8ff9e",
            "#9ecaff"
        )
    }
    val randomColor = randomColors.random()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable {
               onNoteEdit(item)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor(randomColor))
        ),
        shape = RoundedCornerShape(
            topEnd = 10.dp,
            topStart = 10.dp,
            bottomStart = 10.dp,
            bottomEnd = 10.dp
        )
    ) {

        Column(

        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(6f)
                        .padding(top = 5.dp, start = 10.dp, end = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.title,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily2
                        ),
                    )
                }

            }

            Text(
                text = item.description,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontFamily2
                ),
                modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dt - " + DateModifier.convertTimeStampToDate(item.date),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = fontFamily2
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = {
                            showDeleteConfirmDialog.value = true
                        },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

            }
        }

        if (showDeleteConfirmDialog.value) {
            NoteDeleteDialog(
                onDismiss = {
                    showDeleteConfirmDialog.value = false
                },
                onDelete = {
                    showDeleteConfirmDialog.value = false
                    onNoteDelete(item)
                })
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAddDialog(onDismiss: () -> Unit, onAddItem: (String) -> Unit) {
    var text = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Item") },
        text = {
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Item Name") }
            )
        },
        confirmButton = {
            Button(onClick = { onAddItem(text.value) }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditDialog(item: NoteModel, onDismiss: () -> Unit, onEditItem: (NoteModel) -> Unit) {
    var text = remember { mutableStateOf(item.title) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Item") },
        text = {
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Item Name") }
            )
        },
        confirmButton = {
            Button(onClick = { onEditItem(item.copy(title = text.value)) }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDeleteDialog(onDismiss: () -> Unit, onDelete: () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Note") },
        text = {
            Text(
                "Are you sure to delete this note ?",
                color = Color.Black
            )
        },
        confirmButton = {
            Button(onClick = { onDelete() }) {
                Text(
                    "Delete",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(
                    "Cancel",
                    color = Color.White
                )
            }
        },
        containerColor = Color.White,
        textContentColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoneyManageTheme {
        val navController = rememberNavController()

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            // Initialize the ViewModel with the repository or mocked data
            val moneyNoteViewModel = NoteViewModel(
                repository = FakeNotesRepository()
            )

            NotesView(
                notesViewModel = moneyNoteViewModel,
                innerPadding = innerPadding,
                navController = navController
            )
        }
    }
}

class FakeNotesRepository : NoteRepository {
    override fun getAllNotes(): Flow<List<NoteModel>> {
        return flow {
            emit(
                listOf(
                    NoteModel(
                        id = 1,
                        date = 515,
                        title = "Hii",
                        description = "thpoj erhoni eronh",
                        noteBgColor = "#c8ff9e"
                    )
                )
            )
        }
    }

    override suspend fun insert(noteModel: NoteModel) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(noteModel: NoteModel) {
        TODO("Not yet implemented")
    }

    override suspend fun update(noteModel: NoteModel) {
        TODO("Not yet implemented")
    }

}
