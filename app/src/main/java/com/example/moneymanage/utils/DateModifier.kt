package com.example.moneymanage.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateModifier {

    fun convertTimeStampToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return format.format(date)
    }
    fun convertOnlyDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return format.format(date)
    }

    fun convertOnlyTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return format.format(date)
    }

    fun convertOnlyMonth(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM", Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return format.format(date)
    }

    fun convertDateToLong(date: String): Long {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val dateString = date
        val date = dateFormat.parse(dateString)
        val timestamp = date?.time ?: 0
        return timestamp
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DatePickerModal(
        onDateSelected: (Long?) -> Unit,
        onDismiss: () -> Unit
    ) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    selectedDayContentColor = Color.White,
                    selectedYearContentColor = Color.White
                )
            )
        }
    }

    @Composable
    fun TimePickerDialog(
        onDismiss: () -> Unit,
        onConfirm: () -> Unit,
        content: @Composable () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("OK")
                }
            },
            text = { content() }
        )
    }

}