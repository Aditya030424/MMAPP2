package com.example.mmapp.Savings

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

class Savings:AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    val SAVINGS_FRAGMENT_CONTAINER_ID=R.id.savingsFragmentContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_savings)
        val tag= Constants.TAG
        val appViewModelFactory = AppViewModelFactory(application)
        val previousActivity=intent.getStringExtra("Previous Activity")
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]
        supportFragmentManager.beginTransaction().add(
            SAVINGS_FRAGMENT_CONTAINER_ID,
            SavingsFragment.newInstance(R.layout.layout_savingsrv,application,previousActivity)
        ).commit()

        val addSavingsButton=findViewById<Button>(R.id.addSavingsButton)
        val showTotalButton=findViewById<Button>(R.id.showTotalButton)
        val gson= GsonBuilder().setPrettyPrinting().create()
        val savingsAndWishesDB= SavingsAndWishesDB.getInstance(this)
        val repository= SavingsAndWishesRepository(savingsAndWishesDB.savingsAndWishesDao())
        val savingsAndWishesViewModel by viewModels<SavingsAndWishesViewModel> {
            SavingsAndWishesViewModelFactory(repository,application)
        }
        lateinit var message:JsonLog
        lateinit var jsonString:String
        lateinit var deed:String
        var amount:Int
        val savingEditText=findViewById<EditText>(R.id.saving)
        val savingAmountEditText=findViewById<EditText>(R.id.savingAmount)
        sharedViewModel.counterSavings.observe(this) {
            if (it > 0) {
                message=JsonLog("Savings Activity Opened",it.toInt(),previousActivity)
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
        sharedViewModel.counterAddSavings.observe(this) {
            if (it > 0) {
                message=JsonLog("AddSavings Button pressed",it.toInt(),previousActivity,true)
                jsonString = gson.toJson(message)
            Log.d(tag,jsonString)
        }
            }
        sharedViewModel.counterShowTotal.observe(this) {
            if (it > 0) {
                message=JsonLog("ShowTotal Button pressed",it.toInt(),previousActivity,true)
                jsonString = gson.toJson(message)
                Log.d(tag,jsonString)


            }
        }

        addSavingsButton.setOnClickListener {
            sharedViewModel.counterAddSavings.value=sharedViewModel.counterAddSavings.value?.plus(1)
            deed=savingEditText.text.toString()
            amount=savingAmountEditText.text.toString().toInt()
            savingsAndWishesViewModel.insertSavings(SavingsEntity(deed=deed,amount=amount))
            supportFragmentManager.beginTransaction().replace(
                SAVINGS_FRAGMENT_CONTAINER_ID,
                SavingsFragment.newInstance(R.layout.layout_savingsrv,application,previousActivity)
            ).commit()
        }



        showTotalButton.setOnClickListener {
            sharedViewModel.counterShowTotal.value=sharedViewModel.counterShowTotal.value?.plus(1)

            supportFragmentManager.beginTransaction().replace(
                SAVINGS_FRAGMENT_CONTAINER_ID,
                SavingsFragment.newInstance(R.layout.layout_savingstotal,application,previousActivity)
            ).commit()

        }
    }


    override fun onResume() {
        super.onResume()
        sharedViewModel.counterSavings.value=sharedViewModel.counterSavings.value?.plus(1)

    }
}