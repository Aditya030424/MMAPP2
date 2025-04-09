package com.example.mmapp.Wishes

import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.EditText

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mmapp.AHome.Constants
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
    val WISHES_FRAGMENT_CONTAINER_ID=R.id.wishesFragmentContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_wishes)
        lateinit var message:JsonLog
        lateinit var jsonString:String
        val tag= Constants.TAG
        val previousActivity=intent.getStringExtra("Previous Activity")

        val addWishButton = findViewById<Button>(R.id.addWishButton)
        val showTotal = findViewById<Button>(R.id.showTotal)
        val clear = findViewById<Button>(R.id.clear)
        val wishAmountEditText = findViewById<EditText>(R.id.wishAmount)
        val wishEditText=findViewById<EditText>(R.id.wish)

        val fragment=WishesFragment.newInstance(R.layout.layout_wishesrv,application,previousActivity)
        supportFragmentManager.beginTransaction().add(WISHES_FRAGMENT_CONTAINER_ID,fragment).commit()

        val appViewModelFactory = AppViewModelFactory(application)
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]
        val gson = GsonBuilder().setPrettyPrinting().create()

        val savingsAndWishesDB = SavingsAndWishesDB.getInstance(this)
        val repository = SavingsAndWishesRepository(savingsAndWishesDB.savingsAndWishesDao())
        val savingsAndWishesViewModel by viewModels<SavingsAndWishesViewModel> {
            SavingsAndWishesViewModelFactory(repository, application)
        }

        addWishButton.setOnClickListener {
            sharedViewModel.counterAddWishes.value = sharedViewModel.counterAddWishes.value?.plus(1)
            val wish = wishEditText.text.toString()
            val wishAmount = wishAmountEditText.text.toString().toInt()
            savingsAndWishesViewModel.insertWishes(WishesEntity(wish = wish, amount = wishAmount, checked = false))
            supportFragmentManager.beginTransaction().replace(WISHES_FRAGMENT_CONTAINER_ID, fragment).commit()
        }

        clear.setOnClickListener {
            sharedViewModel.counterClearWishes.value = sharedViewModel.counterClearWishes.value?.plus(1)
            fragment.clearChecked()
            supportFragmentManager.beginTransaction().replace(WISHES_FRAGMENT_CONTAINER_ID, fragment).commit()
        }

        showTotal.setOnClickListener {
            sharedViewModel.counterShowTotalWishes.value = sharedViewModel.counterShowTotalWishes.value?.plus(1)
            supportFragmentManager.beginTransaction().replace(WISHES_FRAGMENT_CONTAINER_ID, WishesFragment.newInstance(R.layout.layout_wishestotal, application,intent.getStringExtra("Previous Activity"))             ).commit()
        }

        sharedViewModel.counterWishes.observe(this) {
            if (it > 0) {
                message= JsonLog("Wishes Activity Opened",it.toInt(),previousActivity)
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
        sharedViewModel.counterAddWishes.observe(this) {
            if (it > 0) {
                message= JsonLog("AddWishes Button pressed",it.toInt(),previousActivity,true)
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
        sharedViewModel.counterClearWishes.observe(this) {
            if (it > 0) {
                message= JsonLog("ClearWishes Button pressed",it.toInt(),previousActivity)
                jsonString = gson.toJson(message)
                Log.d(tag,jsonString)
            }
        }
        sharedViewModel.counterShowTotalWishes.observe(this) {
            if (it > 0) {
                message= JsonLog("ShowTotalWishes Button pressed",it.toInt(),previousActivity,true)
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.counterWishes.value = sharedViewModel.counterWishes.value?.plus(1)

    }
}