package com.example.moneymanage.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "money_manage")
data class MoneyManageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amountType: String,
    val amount: Int,
    val category: String,
    val account: String,
    val title: String,
    val description: String,
    val image: String,
    val timeStamp: Long,
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(amountType)
        parcel.writeInt(amount)
        parcel.writeString(category)
        parcel.writeString(account)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeLong(timeStamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoneyManageModel> {
        override fun createFromParcel(parcel: Parcel): MoneyManageModel {
            return MoneyManageModel(parcel)
        }

        override fun newArray(size: Int): Array<MoneyManageModel?> {
            return arrayOfNulls(size)
        }
    }
}