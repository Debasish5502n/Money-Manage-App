package com.example.moneymanage.utils

import android.widget.Spinner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneymanage.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyDialog(onDismiss: () -> Unit, onSubmit: (String, String) -> Unit) {

    val options = listOf("2024", "2023", "2022", "2021", "2020")

    var expanded = remember { mutableStateOf(false) }
    var selectedYearText = remember { mutableStateOf(options[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("Select Month")

                Column {
                    Text(text = selectedYearText.value+" ▼",
                        modifier = Modifier.clickable {
                            expanded.value = !expanded.value
                        })
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = {
                            expanded.value = false
                        }
                    ) {
                        options.forEach { year ->
                            Text(year,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                    selectedYearText.value = year
                                    expanded.value = false
                                })
                        }

                    }
                }


            }

        },
        text = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text("January",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "01") }
                    )
                    Text("February",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "02") }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text("March",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "03") }
                    )
                    Text("April",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "04") }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text("May",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "05") }
                    )
                    Text("June",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "05") }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text("July",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "07") }
                    )
                    Text("August",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "08") }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text("September",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "09") }
                    )
                    Text("October",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "10") }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text("November",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "11") }
                    )
                    Text("December",
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable { onSubmit(selectedYearText.value, "12") }
                    )
                }

            }

        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cancel",
                    color = Color.White
                )
            }
        }
    )
}