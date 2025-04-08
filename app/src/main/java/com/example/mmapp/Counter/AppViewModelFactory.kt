package com.example.mmapp.Counter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SharedViewModel::class.java)){
            return SharedViewModel(application) as T
        }
        else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}