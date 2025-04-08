package com.example.mmapp.Wishes

import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.EditText

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mmapp.Counter.AppViewModelFactory
import com.example.mmapp.Counter.JsonLog
import com.example.mmapp.Counter.SharedViewModel
import com.example.mmapp.Database.SavingsAndWishesDB
import com.example.mmapp.Database.SavingsAndWishesRepository
import com.example.mmapp.Database.SavingsAndWishesViewModel
import com.example.mmapp.Database.SavingsAndWishesViewModelFactory
import com.example.mmapp.R
import com.google.gson.GsonBuilder

class Wishes : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_wishes)
        val appViewModelFactory = AppViewModelFactory(application)
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]
        val fragment=WishesFragment.newInstance(R.layout.layout_wishesrv,application,intent.getStringExtra("Previous Activity"))
        supportFragmentManager.beginTransaction().add(R.id.wishesFragmentContainer,fragment).commit()

        val addWishButton = findViewById<Button>(R.id.addWishButton)
        val showTotal = findViewById<Button>(R.id.showTotal)
        val clear = findViewById<Button>(R.id.clear)
        val wishAmountEditText = findViewById<EditText>(R.id.wishAmount)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val savingsAndWishesDB = SavingsAndWishesDB.getInstance(this)
        val repository = SavingsAndWishesRepository(savingsAndWishesDB.savingsAndWishesDao())
        val savingsAndWishesViewModel by viewModels<SavingsAndWishesViewModel> {
            SavingsAndWishesViewModelFactory(repository, application)
        }
        sharedViewModel.counterWishes.observe(this) {
            if (it > 0) {
                val message= JsonLog("Wishes Activity Opened",it.toString().toInt(),"No Previous Activity")
                val jsonString = gson.toJson(message)
                Log.d("Tracking", jsonString)
            }
        }
        sharedViewModel.counterAddWishes.observe(this) {
            if (it > 0) {
                val message= JsonLog("AddWishes Button pressed",it.toString().toInt(),"Wishes",true)
                val jsonString = gson.toJson(message)
                Log.d("Tracking", jsonString)
            }
        }
        sharedViewModel.counterClearWishes.observe(this) {
            if (it > 0) {
                val message= JsonLog("ClearWishes Button pressed",it.toString().toInt(),"Wishes")
                val jsonString = gson.toJson(message)
                Log.d("Tracking",jsonString)
            }
        }
        sharedViewModel.counterShowTotalWishes.observe(this) {
            if (it > 0) {
                val message= JsonLog("ShowTotalWishes Button pressed",it.toString().toInt(),"Wishes",true)
                val jsonString = gson.toJson(message)
                Log.d("Tracking", jsonString)
            }
        }

        addWishButton.setOnClickListener {
            sharedViewModel.counterAddWishes.value = sharedViewModel.counterAddWishes.value?.plus(1)
            val wish = findViewById<EditText>(R.id.wish).text.toString()
            val wishAmount = wishAmountEditText.text.toString().toInt()
            savingsAndWishesViewModel.insertWishes(WishesEntity(wish = wish, amount = wishAmount, checked = false))
            supportFragmentManager.beginTransaction().replace(R.id.wishesFragmentContainer, fragment).commit()
        }

        clear.setOnClickListener {
            sharedViewModel.counterClearWishes.value = sharedViewModel.counterClearWishes.value?.plus(1)
            fragment.clearChecked()
            supportFragmentManager.beginTransaction().replace(R.id.wishesFragmentContainer, fragment).commit()
        }

        showTotal.setOnClickListener {
            sharedViewModel.counterShowTotalWishes.value = sharedViewModel.counterShowTotalWishes.value?.plus(1)
            supportFragmentManager.beginTransaction().replace(R.id.wishesFragmentContainer, WishesFragment.newInstance(R.layout.layout_wishestotal, application,intent.getStringExtra("Previous Activity"))             ).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.counterWishes.value = sharedViewModel.counterWishes.value?.plus(1)

    }
}