package com.example.moneymanage.features.home.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.features.home.repository.MoneyManageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoneyManageViewModel @Inject constructor(private val repository: MoneyManageRepository)
    : ViewModel() {

    // MutableStateFlow to hold and modify the list
    private val _moneyManageList = MutableStateFlow<List<MoneyManageModel>>(emptyList())
    val moneyManageList: StateFlow<List<MoneyManageModel>> get() = _moneyManageList

    init {
        // Initialize the flow by collecting from the repository
        viewModelScope.launch {
            repository.getTodayMoney(timeStamp = System.currentTimeMillis()).collect { todayList ->
                _moneyManageList.value = todayList
            }
        }
    }

    fun fetchAllMoneyManageList(){
        viewModelScope.launch {
            repository.getAllMoney().collect { moneyList ->
                _moneyManageList.value = moneyList
            }
        }
    }

    fun fetchMoneyManageMonthlyList(year: String, month: String) {
        viewModelScope.launch {
            repository.getMonthlyMoney(year = year, month = month).collect { todayList ->
                _moneyManageList.value = todayList
            }
        }
    }

    fun fetchMoneyManageTodayList(timeStamp: Long) {
        viewModelScope.launch {
            repository.getTodayMoney(timeStamp = timeStamp).collect { todayList ->
                _moneyManageList.value = todayList
            }
        }
    }

    fun insertMoney(
        context: Context,
        moneyManageModel: MoneyManageModel,
        onInsert: () -> Unit
    ) = viewModelScope.launch {
        if (moneyManageModel.amount == 0){
            Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (moneyManageModel.title == ""){
            Toast.makeText(context, "Enter Title", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (moneyManageModel.category == ""){
            Toast.makeText(context, "Select Category", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (moneyManageModel.account == ""){
            Toast.makeText(context, "Select Account", Toast.LENGTH_SHORT).show()
            return@launch
        }
        repository.insertMoney(moneyManageModel)
        onInsert()
    }

    fun deleteMoney(moneyManageModel: MoneyManageModel) = viewModelScope.launch {
        repository.deleteMoney(moneyManageModel)
    }

    fun updateMoney(
        context: Context,
        moneyManageModel: MoneyManageModel,
        onUpdate: () -> Unit
    ) = viewModelScope.launch {
        if (moneyManageModel.amount == 0){
            Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (moneyManageModel.title == ""){
            Toast.makeText(context, "Enter Title", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (moneyManageModel.category == ""){
            Toast.makeText(context, "Select Category", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (moneyManageModel.account == ""){
            Toast.makeText(context, "Select Account", Toast.LENGTH_SHORT).show()
            return@launch
        }
        repository.updateMoney(moneyManageModel)
        onUpdate()
    }

}