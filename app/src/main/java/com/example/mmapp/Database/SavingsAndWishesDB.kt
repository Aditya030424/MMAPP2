package com.example.mmapp.Database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mmapp.Savings.SavingsEntity
import com.example.mmapp.Wishes.WishesEntity


@Database(entities = [SavingsEntity::class, WishesEntity::class], version = 3, exportSchema = true,autoMigrations = [AutoMigration(from = 1, to = 2),AutoMigration(from = 2, to = 3)])
abstract class SavingsAndWishesDB:RoomDatabase() {
    abstract fun savingsAndWishesDao():SavingsAndWishesDao
    companion object {
        @Volatile
        private var INSTANCE: SavingsAndWishesDB? = null


        fun getInstance(context: Context): SavingsAndWishesDB {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavingsAndWishesDB::class.java,
                    "savings_and_wishes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
