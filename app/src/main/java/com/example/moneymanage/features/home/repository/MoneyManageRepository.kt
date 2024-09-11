package com.example.moneymanage.features.home.repository

import com.example.moneymanage.data.local.entity.MoneyManageModel
import kotlinx.coroutines.flow.Flow

interface MoneyManageRepository {

    fun getAllMoney(): Flow<List<MoneyManageModel>>

    fun getMonthlyMoney(year: String, month: String): Flow<List<MoneyManageModel>>

    fun getTodayMoney(timeStamp: Long): Flow<List<MoneyManageModel>>

    suspend fun insertMoney(moneyManageModel: MoneyManageModel)

    suspend fun deleteMoney(moneyManageModel: MoneyManageModel)

    suspend fun updateMoney(moneyManageModel: MoneyManageModel)

}