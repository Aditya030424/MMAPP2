package com.example.mmapp.Wishes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Wishes")
data class WishesEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                        @ColumnInfo(name="Wish") val wish:String,
                        @ColumnInfo(name="Amount") val amount:Int,
                        @ColumnInfo(name="checked",defaultValue = "false") var checked:Boolean,
                        @ColumnInfo(name="pref", defaultValue = "0")val pref:Int=0)
