package com.example.moneymanage.features.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.features.home.repository.MoneyManageRepository
import com.example.moneymanage.features.home.viewModel.MoneyManageViewModel
import com.example.moneymanage.features.navigation.MoneyManageRouteScreen
import com.example.moneymanage.ui.theme.Blue
import com.example.moneymanage.ui.theme.MoneyManageTheme
import com.example.moneymanage.ui.theme.Primary
import com.example.moneymanage.ui.theme.fontFamily2
import com.example.moneymanage.utils.DateModifier
import com.example.moneymanage.utils.MonthlyDialog
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    moneyManageViewModel: MoneyManageViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current

    val totalMoneyManageList = moneyManageViewModel.moneyManageList.collectAsStateWithLifecycle(initialValue = emptyList())

    var balanceReport = rememberSaveable { mutableStateOf("today") }
    var totalBalance = rememberSaveable { mutableStateOf("0") }
    var totalIncome = rememberSaveable { mutableStateOf("0") }
    var totalExpense = rememberSaveable { mutableStateOf("0") }

    val showMonthlyDialog = remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }

    fun calculateTotals(moneyManageList: List<MoneyManageModel>) {
        var calculatedIncome = 0
        var calculatedExpense = 0

        for (i in moneyManageList) {
            if (i.amountType == "Income") {
                calculatedIncome += i.amount
            }
            if (i.amountType == "Expense") {
                calculatedExpense += i.amount
            }
        }

        totalBalance.value = (calculatedIncome - calculatedExpense).toString()
        totalIncome.value = calculatedIncome.toString()
        totalExpense.value = calculatedExpense.toString()
    }

    calculateTotals(totalMoneyManageList.value)

    LaunchedEffect(Unit) {
        // Perform initial calculations
        delay(1000)
        calculateTotals(totalMoneyManageList.value)
        // Simulate delay and add dummy data
    }

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(MoneyManageRouteScreen.AddMoney.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                containerColor = Color.White,
                contentColor = Primary,
                modifier = Modifier.padding(innerPadding)
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
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White) // Set your desired color here
            ) {
                Column {

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Today",
                            color = if (balanceReport.value == "today") Primary else Color.LightGray,
                            fontSize = 15.sp,
                            modifier = Modifier.clickable {
                                balanceReport.value = "today"
                                moneyManageViewModel.fetchMoneyManageTodayList(System.currentTimeMillis())
                                calculateTotals(totalMoneyManageList.value)
                            },
                            fontFamily = fontFamily2
                        )
                        Text(
                            text = "Monthly",
                            color = if (balanceReport.value == "monthly") Primary else Color.LightGray,
                            fontSize = 15.sp,
                            modifier = Modifier.clickable {
                                showMonthlyDialog.value = true
                            },
                            fontFamily = fontFamily2
                        )
                        Text(
                            text = "Calendar",
                            color = if (balanceReport.value == "calendar") Primary else Color.LightGray,
                            fontSize = 15.sp,
                            modifier = Modifier.clickable {
                                showDatePicker.value = true
                            },
                            fontFamily = fontFamily2
                        )
                        Text(
                            text = "Total",
                            color = if (balanceReport.value == "total") Primary else Color.LightGray,
                            fontSize = 15.sp,
                            modifier = Modifier.clickable {
                                balanceReport.value = "total"
                                moneyManageViewModel.fetchAllMoneyManageList()
                                calculateTotals(totalMoneyManageList.value)
                            },
                            fontFamily = fontFamily2
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .background(Color.Black.copy(0.2f))
                            .height(1.dp)
                            .fillMaxWidth()
                    )

                }

            }

            Box(
                modifier = Modifier
                    .background(
                        Color.LightGray.copy(0.2f)
                    )
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                // Add your content here
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.White
                            )
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 5.dp)

                    ) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Income",
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier,
                                fontFamily = fontFamily2
                            )
                            BasicText(
                                text = totalIncome.value,
                                style = TextStyle(
                                    color = Blue,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = fontFamily2
                                ),
                                modifier = Modifier.padding(0.dp)
                            )
                        }

                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Expenses",
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontFamily = fontFamily2
                            )
                            BasicText(
                                text = totalExpense.value,
                                style = TextStyle(
                                    color = Primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = fontFamily2
                                ),
                                modifier = Modifier.padding(0.dp)
                            )
                        }

                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total",
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.clickable { },
                                fontFamily = fontFamily2
                            )
                            BasicText(
                                text = totalBalance.value,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = fontFamily2
                                ),
                                modifier = Modifier.padding(0.dp)
                            )
                        }

                    }

                    Spacer(
                        modifier = Modifier
                            .height(0.5.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .background(Color.Black.copy(alpha = 0.3f))
                    )

                    if (totalMoneyManageList.value.isEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "No Records Found",
                                color = Color.LightGray,
                                fontSize = 20.sp,
                                modifier = Modifier.clickable { },
                                fontFamily = fontFamily2
                            )
                        }

                    }

                    LazyColumn(
                        modifier = Modifier
                            .background(
                                Color.White
                            )
                    ) {
                        itemsIndexed(
                            items = totalMoneyManageList.value,
                            key = { _, listItem ->
                                listItem.hashCode()
                            }
                        ) { index, item ->

                            val coroutineScope = rememberCoroutineScope()
                            val state = rememberSwipeToDismissBoxState(
                                confirmValueChange = { state ->
                                    if (state == SwipeToDismissBoxValue.EndToStart) {
                                        coroutineScope.launch {
                                            moneyManageViewModel.deleteMoney(item)
                                            calculateTotals(totalMoneyManageList.value)
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
                                    MoneyManageItem(
                                        item = item,
                                        modifier = Modifier.clickable {
                                            val json = Gson().toJson(item)
                                            navController.navigate(
                                                MoneyManageRouteScreen.UpdateMoney.route + "/$json"
                                            )
                                        }
                                    )
                                })

                        }

                    }

                }

                // Add your content here
            }
        }

        if(showMonthlyDialog.value){
            MonthlyDialog(
                onDismiss = { showMonthlyDialog.value = false },
                onSubmit = { year, month ->
                    balanceReport.value = "monthly"
                    moneyManageViewModel.fetchMoneyManageMonthlyList(year = year, month = month)
                    showMonthlyDialog.value = false
                }
            )
        }

        if (showDatePicker.value){
            DateModifier.DatePickerModal(
                onDateSelected = { date ->
                    var dateText1 = DateModifier.convertOnlyDate(date!!)
                    balanceReport.value = "calendar"
                    moneyManageViewModel.fetchMoneyManageTodayList(date)
                    calculateTotals(totalMoneyManageList.value)
                    showDatePicker.value = false
                },
                onDismiss = {
                    showDatePicker.value = false
                }
            )
        }

    }
}

@Composable
fun MoneyManageItem(
    item: MoneyManageModel,
    modifier: Modifier) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(Color.White)
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            BasicText(
                text = item.category,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontFamily = fontFamily2
                ),
                modifier = Modifier
                    .padding(0.dp)
                    .weight(1.5f)
            )

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                BasicText(
                    text = item.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontFamily = fontFamily2
                    ),
                    modifier = Modifier.padding(0.dp),
                    maxLines = 1
                )
                BasicText(
                    text = item.account,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontFamily = fontFamily2
                    ),
                    modifier = Modifier
                        .padding(0.dp)
                        .padding(top = 2.dp)
                )
            }

            Text(
                text = "₹ " + item.amount,
                style = TextStyle(
                    color = if (item.amountType == "Expense") Primary else Blue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily2
                ),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(0.dp)
                    .weight(1f)
            )
        }

    }
}

@Composable
fun CustomerCircularProgressBar(
    progress: Float = 0f,
    size: Dp = 96.dp,
    textSize: TextUnit = 15.sp,
    strokeWidth: Dp = 12.dp,
    progressColor: Color = Color.LightGray,
    backgroundArcColor: Color = Color.LightGray,
) {
    val progressText = "${(progress * 100).toInt()}%"

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            // Background Arc
            drawArc(
                color = backgroundArcColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = Size(size.toPx(), size.toPx()),
                style = Stroke(width = strokeWidth.toPx())
            )
            // Progress Arc
            withTransform({
                rotate(degrees = 270f, pivot = center)
            }) {
                drawArc(
                    color = progressColor,
                    startAngle = 0f,
                    sweepAngle = progress * 360,
                    useCenter = false,
                    size = Size(size.toPx(), size.toPx()),
                    style = Stroke(width = strokeWidth.toPx())
                )
            }
        }
        // Centered Text
        Text(
            text = progressText,
            color = Color.White,
            fontSize = textSize
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview11() {
    MoneyManageTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()

            // Initialize the ViewModel with the repository or mocked data
            val moneyManageViewModel = MoneyManageViewModel(
                repository = FakeMoneyManageRepository()
            )

            HomeScreen(
                moneyManageViewModel = moneyManageViewModel,
                innerPadding = innerPadding,
                navController = navController
            )
        }

    }
}

class FakeMoneyManageRepository : MoneyManageRepository {

    override fun getAllMoney(): Flow<List<MoneyManageModel>> {
        return flow {
            emit(
                listOf(
                    MoneyManageModel(
                        id = 1,
                        amountType = "Income",
                        amount = 5000,
                        category = "\uD83D\uDCB0 Salary",
                        account = "Bank Account",
                        title = "Monthly Salary",
                        description = "Salary for the month of January",
                        image = "https://example.com/salary_image.png",
                        timeStamp = 1643616000000L
                    )
                )
            )
        }
    }

    override fun getMonthlyMoney(year: String, month: String): Flow<List<MoneyManageModel>> {
        TODO("Not yet implemented")
    }

    override fun getTodayMoney(timeStamp: Long): Flow<List<MoneyManageModel>> {
        return flow {
            emit(
                listOf(
                    MoneyManageModel(
                        id = 1,
                        amountType = "Income",
                        amount = 5000,
                        category = "\uD83D\uDCB0 Salary",
                        account = "Bank Account",
                        title = "Monthly Salary",
                        description = "Salary for the month of January",
                        image = "https://example.com/salary_image.png",
                        timeStamp = 1643616000000L
                    )
                )
            )
        }
    }

    override suspend fun insertMoney(moneyManageModel: MoneyManageModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMoney(moneyManageModel: MoneyManageModel) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMoney(moneyManageModel: MoneyManageModel) {
        TODO("Not yet implemented")
    }
}