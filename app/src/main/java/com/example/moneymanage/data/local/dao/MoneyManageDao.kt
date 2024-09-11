package com.example.moneymanage.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moneymanage.data.local.entity.MoneyManageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyManageDao {
    @Query("SELECT * FROM money_manage ORDER BY id DESC")
    fun getAllMoney(): Flow<List<MoneyManageModel>>

    @Query("SELECT * FROM money_manage ORDER BY id DESC")
    fun getFilteredMoney(): Flow<List<MoneyManageModel>>

    @Query("SELECT * FROM money_manage ORDER BY id DESC")
    fun getTodayMoney(): Flow<List<MoneyManageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoney(moneyManageModel: MoneyManageModel)

    @Delete
    suspend fun deleteMoney(moneyManageModel: MoneyManageModel)

    @Update
    suspend fun updateMoney(moneyManageModel: MoneyManageModel)
}
