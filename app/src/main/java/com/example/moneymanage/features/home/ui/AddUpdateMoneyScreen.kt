package com.example.moneymanage.features.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.R
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.ui.theme.Blue
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary
import com.example.moneymanage.utils.DateModifier
import com.example.moneymanage.utils.DateModifier.TimePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddUpdateMoneyScreen(
    moneyManageViewModel: MoneyManageViewModel = hiltViewModel(),
    moneyManageModel: MoneyManageModel?,
    navController: NavController?
) {
    val context = LocalContext.current

    var amountType = remember { mutableStateOf( if (moneyManageModel != null){
        if (moneyManageModel.amountType == "Income"){
            "income"
        } else {
            "expense"
        }
    }else{"income"} ) }
    var categoryText = rememberSaveable { mutableStateOf( if (moneyManageModel != null){ moneyManageModel.category }else{""} ) }
    var accountText = rememberSaveable { mutableStateOf( if (moneyManageModel != null){ moneyManageModel.account }else{""} ) }
    var amountText = rememberSaveable { mutableStateOf( if (moneyManageModel != null){ moneyManageModel.amount.toString() }else{""} ) }
    var titleText = rememberSaveable { mutableStateOf( if (moneyManageModel != null){ moneyManageModel.title }else{""} ) }
    var descriptionText = rememberSaveable { mutableStateOf( if (moneyManageModel != null){ moneyManageModel.description }else{""} ) }

    var dateText = rememberSaveable { mutableStateOf(DateModifier.convertOnlyDate(
        if (moneyManageModel != null) {
            moneyManageModel.timeStamp
        } else {
            System.currentTimeMillis()
        }
    )) }
    var timeText = rememberSaveable { mutableStateOf(DateModifier.convertOnlyTime(
        if (moneyManageModel != null) {
            moneyManageModel.timeStamp
        } else {
            System.currentTimeMillis()
        }
    )) }
    var dateWithTimeText = rememberSaveable {
        mutableStateOf(
            if (moneyManageModel != null) {
                moneyManageModel.timeStamp.toString()
            } else {
                System.currentTimeMillis().toString()
            }
        )
    }

    val selectCategory = remember { mutableStateOf(false) }
    val selectAccount = remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(0, 0, true)

    Scaffold{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
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
                    text = if (amountType.value == "income") "Income" else "Expense",
                    fontSize = 20.sp,
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ){
                    Button(
                        onClick = {
                            amountType.value = "income"
                            categoryText.value = ""
                            accountText.value = ""
                            titleText.value = ""
                            amountText.value = ""
                        },
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 1.dp,
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (amountType.value == "income") Blue else Color.Gray,
                            containerColor = if (amountType.value == "income") Color.White else Color.LightGray.copy(0.1f)
                        ),
                        border = BorderStroke(1.dp, if (amountType.value == "income") Blue else Color.Gray),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp)
                    ) {
                        Text("Income")
                    }

                    Button(
                        onClick = {
                            amountType.value = "expense"
                            categoryText.value = ""
                            accountText.value = ""
                            titleText.value = ""
                            amountText.value = ""
                        },
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 1.dp,
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (amountType.value == "expense") Primary else Color.Gray,
                            containerColor = if (amountType.value == "expense") Color.White else Color.LightGray.copy(0.1f)
                        ),
                        border = BorderStroke(1.dp, if (amountType.value == "expense") Primary else Color.Gray),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp)
                    ) {
                        Text("Expense")
                    }

                }

                //Amount EditText
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 10.dp)
                ){
                    Text(
                        text = "Amount: ",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier.weight(2f)
                    ){
                        BasicTextField(
                            value = amountText.value,
                            onValueChange = { newValue ->
                            // Filter out non-numeric characters
                                if (newValue.all { it.isDigit() }) {
                                    amountText.value = newValue
                                }
                            },
                            textStyle = TextStyle(fontSize = 15.sp),
                            cursorBrush = SolidColor(if (amountType.value == "income") Blue else Primary),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Done
                            )
                        )
                        Divider(
                            color = if (amountType.value == "income") Blue else Primary, // Line color
                            thickness = 1.dp,    // Line thickness
                            modifier = Modifier
                                .fillMaxWidth() // Makes the divider span the width of the column
                                .padding(top = 8.dp) // Adjust the space between text field and divider
                        )
                    }

                }
                //Amount EditText

                //Category EditText
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 10.dp)
                ){
                    Text(
                        text = "Category: ",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .clickable { selectCategory.value = true }
                    ){
                        Text(
                            text = categoryText.value,
                            fontSize = 15.sp
                        )
                        Divider(
                            color = Color.Gray, // Line color
                            thickness = 1.dp,    // Line thickness
                            modifier = Modifier
                                .fillMaxWidth() // Makes the divider span the width of the column
                                .padding(top = 8.dp) // Adjust the space between text field and divider
                        )
                    }

                }
                //Category EditText

                //Account EditText
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 10.dp)
                ){
                    Text(
                        text = "Account: ",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .clickable { selectAccount.value = true }
                    ){
                        Text(
                            text = accountText.value,
                            fontSize = 15.sp
                        )
                        Divider(
                            color = Color.Gray, // Line color
                            thickness = 1.dp,    // Line thickness
                            modifier = Modifier
                                .fillMaxWidth() // Makes the divider span the width of the column
                                .padding(top = 8.dp) // Adjust the space between text field and divider
                        )
                    }

                }
                //Account EditText

                //Title EditText
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 10.dp)
                ){
                    Text(
                        text = "Title: ",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier.weight(2f)
                    ){
                        BasicTextField(
                            value = titleText.value,
                            onValueChange = {
                                titleText.value = it.toString()
                            },
                            textStyle = TextStyle(fontSize = 15.sp),
                            cursorBrush = SolidColor(if (amountType.value == "income") Blue else Primary)
                        )
                        Divider(
                            color = Color.Gray, // Line color
                            thickness = 1.dp,    // Line thickness
                            modifier = Modifier
                                .fillMaxWidth() // Makes the divider span the width of the column
                                .padding(top = 8.dp) // Adjust the space between text field and divider
                        )
                    }

                }
                //Title EditText

                //Date EditText
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 10.dp)
                ){
                    Text(
                        text = "Date: ",
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f),
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier.weight(2f)
                    ){
                        Row {
                            Text(
                                text = dateText.value,
                                color = Color.Black,
                                fontSize = 15.sp,
                                modifier = Modifier
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

                //Desc EditText
                TextField(
                    value = descriptionText.value,
                    onValueChange = { descriptionText.value = it },
                    label = { Text("Enter Description") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = if (amountType.value == "income") Blue.copy(0.1f) else Primary.copy(0.1f),
                        cursorColor = Color.Blue,
                        focusedIndicatorColor = if (amountType.value == "income") Blue else Primary,
                        unfocusedIndicatorColor = if (amountType.value == "income") Blue else Primary,
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null,
                            tint = if (amountType.value == "expense") Primary else Blue
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                //Desc EditText

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

                if (moneyManageModel != null) {
                    Button(
                        onClick = {
                            moneyManageViewModel.updateMoney(
                                context,
                                MoneyManageModel(
                                    id = moneyManageModel.id.toLong(),
                                    amountType = if (amountType.value == "income") "Income" else "Expense",
                                    amount = if (amountText.value.isEmpty()) {
                                        0
                                    } else {
                                        amountText.value.toInt()
                                    },
                                    category = categoryText.value,
                                    account = accountText.value,
                                    title = titleText.value,
                                    description = descriptionText.value,
                                    image = "",
                                    timeStamp = moneyManageModel.timeStamp.toLong()
                                ),
                                onUpdate = {
                                    navController?.popBackStack()
                                }
                            )

                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (amountType.value == "expense") Primary else Blue
                        )
                    ) {
                        Text(
                            text = "Update",
                            color = Color.White
                        )
                    }
                }else{
                    Button(
                        onClick = {
                            moneyManageViewModel.insertMoney(
                                context,
                                MoneyManageModel(
                                    id = dateWithTimeText.value.toLong(),
                                    amountType = if (amountType.value == "income") "Income" else "Expense",
                                    amount = if (amountText.value.isEmpty()) {
                                        0
                                    } else {
                                        amountText.value.toInt()
                                    },
                                    category = categoryText.value,
                                    account = accountText.value,
                                    title = titleText.value,
                                    description = descriptionText.value,
                                    image = "",
                                    timeStamp = dateWithTimeText.value.toLong()
                                ),
                                onInsert = {
                                    navController?.popBackStack()
                                }
                            )

                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (amountType.value == "expense") Primary else Blue
                        )
                    ) {
                        Text(
                            text = "Add",
                            color = Color.White
                        )
                    }
                }

            }
            //Submit Btn

            if (selectCategory.value){
                selectCategoryDialog(
                    amountType = amountType.value,
                    onDismiss = { selectCategory.value = false },
                    onClicked = { category ->
                        categoryText.value = category
                        selectCategory.value = false
                    }
                )
            }

            if (selectAccount.value){
                selectAccountDialog(
                    onDismiss = { selectAccount.value = false },
                    onClicked = { account ->
                        accountText.value = account
                        selectAccount.value = false
                    }
                )
            }
            
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selectCategoryDialog(
    amountType: String,
    onDismiss: () -> Unit,
    onClicked: (String) -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Category") },
        containerColor = Color.White,
        text = {
            if (amountType.toString() == "expense") {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "\uD83E\uDD58 Food",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83E\uDD58 Food") }
                        )
                        Text(
                            text = "\uD83D\uDC6B Social Life",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDC6B Social Life") }
                        )
                        Text(
                            text = "\uD83D\uDC36 Pets",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDC36 Pets") }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            text = "\uD83D\uDC36 Pets",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDC36 Pets") }
                        )
                        Text(
                            text = "\uD83D\uDE99 Transport",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDE99 Transport") }
                        )
                        Text(
                            text = "\uD83C\uDFB8 Culture",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83C\uDFB8 Culture") }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            text = "\uD83D\uDC55 Clothes",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDC55 Clothes") }
                        )
                        Text(
                            text = "\uD83D\uDC84 Beauty",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDC84 Beauty") }
                        )
                        Text(
                            text = "\uD83D\uDC8A Health",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDC8A Health") }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            text = "\uD83D\uDCDA Education",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDCDA Education") }
                        )
                        Text(
                            text = "\uD83C\uDF81 Gift",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83C\uDF81 Gift") }
                        )
                        Text(
                            text = "Other",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("Other") }
                        )
                    }

                }
            }else{
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "\uD83D\uDCB5 Petty cash",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDCB5 Petty cash") }
                        )
                        Text(
                            text = "\uD83D\uDCB0 Salary",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDCB0 Salary") }
                        )

                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            text = "\uD83E\uDD11 Allowance",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83E\uDD11 Allowance") }
                        )
                        Text(
                            text = "\uD83E\uDD47 Bonus",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83E\uDD47 Bonus") }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            text = "\uD83D\uDCBC PF",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("\uD83D\uDCBC PF") }
                        )
                        Text(
                            text = "Other",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable { onClicked("Other") }
                        )
                    }

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

@Composable
fun selectAccountDialog(
    onDismiss: () -> Unit,
    onClicked: (String) -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Account") },
        containerColor = Color.White,
        text = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cash",
                        fontSize = 17.sp,
                        modifier = Modifier.clickable { onClicked("Cash") }
                    )
                    Text(
                        text = "Account",
                        fontSize = 17.sp,
                        modifier = Modifier.clickable { onClicked("Account") }
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = "Card",
                        fontSize = 17.sp,
                        modifier = Modifier.clickable { onClicked("Card") }
                    )
                    Text(
                        text = "Other",
                        fontSize = 17.sp,
                        modifier = Modifier.clickable { onClicked("Other") }
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

@Preview(showBackground = true)
@Composable
fun AddMoneyPreview() {
    MoneyManageTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()

            // Initialize the ViewModel with the repository or mocked data
            val moneyManageViewModel = MoneyManageViewModel(
                repository = FakeMoneyManageRepository()
            )

            AddUpdateMoneyScreen(
                moneyManageViewModel = moneyManageViewModel,
                moneyManageModel = null,
                navController = navController
            )
        }

    }
}