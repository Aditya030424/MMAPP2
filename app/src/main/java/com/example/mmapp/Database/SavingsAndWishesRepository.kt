package com.example.mmapp.Database

import com.example.mmapp.Savings.SavingsEntity
import com.example.mmapp.Wishes.WishesEntity
import kotlinx.coroutines.flow.Flow

class SavingsAndWishesRepository(private val savingsAndWishesDao: SavingsAndWishesDao) {
    suspend fun insertSavings(savingsEntity: SavingsEntity) {
        savingsAndWishesDao.insertSavings(savingsEntity)
    }
    suspend fun insertWishes(wishesEntity: WishesEntity) {
        savingsAndWishesDao.insertWishes(wishesEntity)
    }
    fun getAllSavings(): Flow<List<SavingsEntity>>? {
        return savingsAndWishesDao.getAllSavings()
    }
    fun getAllWishes(): Flow<List<WishesEntity>>? {
        return savingsAndWishesDao.getAllWishes()
    }
    fun getTotalSavings(): Flow<Int> {
        return savingsAndWishesDao.getTotalSavings()
    }
    fun getTotalWishes(): Flow<Int> {
        return savingsAndWishesDao.getTotalWishes()
    }
    suspend fun updateCheckedTo1(wishesEntity: WishesEntity) {
        savingsAndWishesDao.updateCheckedTo1(wishesEntity.id)
    }
    suspend fun updateCheckedTo0(wishesEntity: WishesEntity) {
        savingsAndWishesDao.updateCheckedTo0(wishesEntity.id)
    }
    suspend fun incrementPref(wishesEntity: WishesEntity) {
        savingsAndWishesDao.incrementPref(wishesEntity.id)
    }
}