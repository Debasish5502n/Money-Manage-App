package com.example.moneymanage.data.repository

import android.widget.Toast
import com.example.moneymanage.data.local.dao.MoneyManageDao
import com.example.moneymanage.data.local.entity.MoneyManageModel
import com.example.moneymanage.features.home.repository.MoneyManageRepository
import com.example.moneymanage.utils.DateModifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoneyManageRepositoryImp @Inject constructor(private val moneyManageDao: MoneyManageDao) : MoneyManageRepository {

    override fun getAllMoney(): Flow<List<MoneyManageModel>> {
        return moneyManageDao.getAllMoney()
    }

    override fun getMonthlyMoney(year: String, month: String): Flow<List<MoneyManageModel>> {
        return moneyManageDao.getAllMoney().map { moneyList ->
            moneyList.filter {
                DateModifier.convertOnlyMonth(it.timeStamp).equals("$year.$month")
            }
        }
    }

    override fun getTodayMoney(timeStamp: Long): Flow<List<MoneyManageModel>> {
        return moneyManageDao.getAllMoney().map { moneyList ->
            moneyList.filter {
                DateModifier.convertOnlyDate(it.timeStamp).equals(DateModifier.convertOnlyDate(timeStamp))
            }
        }
    }

    override suspend fun insertMoney(moneyManageModel: MoneyManageModel) {
        moneyManageDao.insertMoney(moneyManageModel)
    }

    override suspend fun deleteMoney(moneyManageModel: MoneyManageModel) {
        moneyManageDao.deleteMoney(moneyManageModel)
    }

    override suspend fun updateMoney(moneyManageModel: MoneyManageModel) {
        moneyManageDao.updateMoney(moneyManageModel)
    }

}