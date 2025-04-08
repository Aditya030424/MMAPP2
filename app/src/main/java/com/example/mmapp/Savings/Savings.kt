package com.example.mmapp.Savings

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

class Savings:AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_savings)
        val appViewModelFactory = AppViewModelFactory(application)
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]
        supportFragmentManager.beginTransaction().add(
            R.id.savingsFragmentContainer,
            SavingsFragment.newInstance(R.layout.layout_savingsrv,application)
        ).commit()

        val addSavingsButton=findViewById<Button>(R.id.addSavingsButton)
        val showTotalButton=findViewById<Button>(R.id.showTotalButton)
        val gson= GsonBuilder().setPrettyPrinting().create()
        val savingsAndWishesDB= SavingsAndWishesDB.getInstance(this)
        val repository= SavingsAndWishesRepository(savingsAndWishesDB.savingsAndWishesDao())
        val savingsAndWishesViewModel by viewModels<SavingsAndWishesViewModel> {
            SavingsAndWishesViewModelFactory(repository,application)
        }
        sharedViewModel.counterSavings.observe(this) {
            if (it > 0) {
                val message=JsonLog("Savings Activity Opened",it.toString().toInt(),"No Previous Activity")
                val jsonString = gson.toJson(message)
                Log.d("Tracking", jsonString)
            }
        }
        sharedViewModel.counterAddSavings.observe(this) {
            if (it > 0) {
                val message=JsonLog("AddSavings Button pressed",it.toString().toInt(),"Savings",true)
                val jsonString = gson.toJson(message)
            Log.d("Tracking",jsonString)
        }
            }
        sharedViewModel.counterShowTotal.observe(this) {
            if (it > 0) {
                val message=JsonLog("ShowTotal Button pressed",it.toString().toInt(),"Savings",true)
                val jsonString = gson.toJson(message)
                Log.d("Tracking",jsonString)


            }
        }

        addSavingsButton.setOnClickListener {
            sharedViewModel.counterAddSavings.value=sharedViewModel.counterAddSavings.value?.plus(1)
            val deed=findViewById<EditText>(R.id.saving).text.toString()
            val amount=findViewById<EditText>(R.id.savingAmount).text.toString().toInt()
            savingsAndWishesViewModel.insertSavings(SavingsEntity(deed=deed,amount=amount))
            supportFragmentManager.beginTransaction().replace(
                R.id.savingsFragmentContainer,
                SavingsFragment.newInstance(R.layout.layout_savingsrv,application)
            ).commit()
        }



        showTotalButton.setOnClickListener {
            sharedViewModel.counterShowTotal.value=sharedViewModel.counterShowTotal.value?.plus(1)

            supportFragmentManager.beginTransaction().replace(
                R.id.savingsFragmentContainer,
                SavingsFragment.newInstance(R.layout.layout_savingstotal,application)
            ).commit()

        }
    }


    override fun onResume() {
        super.onResume()
        sharedViewModel.counterSavings.value=sharedViewModel.counterSavings.value?.plus(1)

    }
}