package com.example.moneymanage.features.notes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.data.local.entity.NoteModel
import com.example.moneymanage.features.home.repository.MoneyManageRepository
import com.example.moneymanage.features.home.ui.FakeMoneyManageRepository
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.features.notes.repository.NoteRepository
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun NotesView(
    notesViewModel: NoteViewModel = hiltViewModel(),
    innerPadding: PaddingValues
) {
    val notes = notesViewModel.notes.observeAsState(initial = emptyList())
    val showDialog = remember { mutableStateOf(false) }
    val currentEditItem = remember { mutableStateOf<NoteModel?>(null) }
    val itemToDelete = remember { mutableStateOf<NoteModel?>(null) }
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .padding(innerPadding)
            .background(color = Color.White),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog.value = true },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
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
                modifier = Modifier.fillMaxSize()
        ) {
            items(notes.value.size) { item ->
                ItemRow(
                    item = notes.value[item],
                    onItemEdit = { editItem ->
                        currentEditItem.value = editItem
                        showDialog.value = true
                    },
                    onItemDelete = { deleteItem ->
                        itemToDelete.value = deleteItem
                        showDeleteConfirmDialog.value = true
                    }
                )
                Spacer(modifier = Modifier.height(8.dp)) // Spacer for padding between items
            }
        }
    }
    }

    // Add Dialog and Delete Confirmation Dialog
    if (showDialog.value) {
        if (currentEditItem.value == null) {
            ItemAddDialog(onDismiss = { showDialog.value = false }, onAddItem = { itemName ->
                notesViewModel.insert(NoteModel(id = 1, title = itemName, description = ""))
                showDialog.value = false
            })
        } else {
            ItemEditDialog(item = currentEditItem.value!!, onDismiss = {
                showDialog.value = false
                currentEditItem.value = null
            }, onEditItem = { editedItem ->
                notesViewModel.update(editedItem)
                showDialog.value = false
                currentEditItem.value = null
            })
        }
    }


    if (showDeleteConfirmDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog.value = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this item?") },
            confirmButton = {
                Button(
                    onClick = {
                        itemToDelete.let { notesViewModel.delete(itemToDelete.value!!) }
                        showDeleteConfirmDialog.value = false
                    }
                ) { Text("Yes") }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirmDialog.value = false }) { Text("No") }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemRow(item: NoteModel, onItemEdit: (NoteModel) -> Unit, onItemDelete: (NoteModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(
            topEnd = 0.dp
        )
    ) {

        Box {

            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(
                        topEnd = 23.dp,
                        topStart = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    ))
                    .background(Primary.copy(0.2f))
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(6f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                        )
                    }

                }


                Text(
                    text = "oih",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )
                Row {
                    IconButton(onClick = { onItemEdit(item) }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { onItemDelete(item) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                rotate(degrees = 0F) {
                    drawArc(
                        color = Primary.copy(0.5f),
                        topLeft = Offset(canvasWidth / 1.11f, -10f),
                        startAngle = 45f, // 0 represents 3'0 clock
                        sweepAngle = 180f, // size of the arc
                        useCenter = true,
                        size = Size(70f, 70f)
                    )
                }
            }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoneyManageTheme {

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            // Initialize the ViewModel with the repository or mocked data
            val moneyNoteViewModel = NoteViewModel(
                repository = FakeNotesRepository()
            )

            NotesView(
                notesViewModel = moneyNoteViewModel,
                innerPadding = innerPadding
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
                        description = ""
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
