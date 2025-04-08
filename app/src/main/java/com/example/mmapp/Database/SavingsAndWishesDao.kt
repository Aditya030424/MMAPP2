package com.example.mmapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mmapp.Savings.SavingsEntity
import com.example.mmapp.Wishes.WishesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingsAndWishesDao
{
    @Insert
    suspend fun insertSavings(savingsEntity: SavingsEntity)

    @Insert
    suspend fun insertWishes(wishesEntity: WishesEntity)

    @Query("SELECT * FROM savings")
    fun getAllSavings(): Flow<List<SavingsEntity>>?

    @Query("SELECT * FROM wishes")
    fun getAllWishes(): Flow<List<WishesEntity>>?

    @Query("SELECT sum(amount) FROM savings")
    fun getTotalSavings(): Flow<Int>

    @Query("SELECT sum(amount) FROM wishes")
    fun getTotalWishes(): Flow<Int>

    @Query("UPDATE wishes SET checked=true WHERE id=:id")
    suspend fun updateCheckedTo1(id:Int)

    @Query("UPDATE wishes SET checked=false WHERE id=:id")
    suspend fun updateCheckedTo0(id: Int)

    @Query("UPDATE wishes SET pref=pref+1 WHERE id=:id")
    suspend fun incrementPref(id: Int)

//    @Query("UPDATE wishes SET pref=0")
//    suspend fun dismissPref
}