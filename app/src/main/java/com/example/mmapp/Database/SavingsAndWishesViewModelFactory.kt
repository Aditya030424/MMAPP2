package com.example.mmapp.Database

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SavingsAndWishesViewModelFactory(private val repository: SavingsAndWishesRepository,private val application: Application) : ViewModelProvider.Factory {
    override fun<T:ViewModel> create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(SavingsAndWishesViewModel::class.java)){
            return SavingsAndWishesViewModel(repository,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}