package com.example.mmapp.Savings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savings")
data class SavingsEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                         @ColumnInfo(name="Deed") val deed:String,
                         @ColumnInfo(name="Amount") val amount:Int)

