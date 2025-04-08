package com.example.mmapp.Database

import androidx.lifecycle.AndroidViewModel
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mmapp.Savings.SavingsEntity
import com.example.mmapp.Wishes.WishesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavingsAndWishesViewModel(private val repository: SavingsAndWishesRepository, application: Application) : AndroidViewModel(application) {
    private var _allSavings: MutableLiveData<List<SavingsEntity>> = MutableLiveData()
    val allSavings: LiveData<List<SavingsEntity>> = _allSavings

    init {
        getAllSavingsTransac()
        getAllWishesTransac()
    }

    fun insertSavings(savingsEntity: SavingsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSavings(savingsEntity)
        }
    }

    fun getAllSavingsTransac() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllSavings()?.collect {
                _allSavings.postValue(it)
            }
        }
    }

    fun insertWishes(wishesEntity: WishesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWishes(wishesEntity)
        }
    }

    private var _allWishes: MutableLiveData<List<WishesEntity>> = MutableLiveData()
    val allWishes: LiveData<List<WishesEntity>> = _allWishes

    fun getAllWishesTransac() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllWishes()?.collect {
                _allWishes.postValue(it)
            }
        }
    }

    private var _totalSavings: MutableLiveData<Int> = MutableLiveData()
    val totalSavings: LiveData<Int> = _totalSavings

    fun getTotalSavingsTransac() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTotalSavings().collect {
                _totalSavings.postValue(it)
            }
        }
    }

    private var _totalWishes: MutableLiveData<Int> = MutableLiveData()
    val totalWishes: LiveData<Int> = _totalWishes

    fun getTotalWishesTransac() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTotalWishes().collect {
                _totalWishes.postValue(it)
            }
        }
    }

    fun updateCheckedTo1(wishesEntity: WishesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCheckedTo1(wishesEntity)
        }
    }

    fun updateCheckedTo0(wishesEntity: WishesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCheckedTo0(wishesEntity)
        }
    }

    fun incrementPref(wishesEntity: WishesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.incrementPref(wishesEntity)
        }
    }

}
