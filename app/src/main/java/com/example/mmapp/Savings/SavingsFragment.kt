package com.example.mmapp.Savings

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

class SavingsFragment(val application:Application): Fragment() {
    private var layoutId:Int?=null
    private var previousActivity:String?=null
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var saving:String
    val gson = GsonBuilder().setPrettyPrinting().create()
    lateinit var message:JsonLog
    lateinit var jsonString:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutId=it.getInt(ARG_LAYOUT_ID)
            previousActivity=it.getString(ARG_PREVIOUS_ACTIVITY)
        }
        val tag= Constants.TAG
        val appViewModelFactory = AppViewModelFactory(application)
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]

        sharedViewModel.counterSavingsScroll.observe(this)
        {
            if(it>0)
            {
                message= JsonLog("Scroll to New Item:$saving",it.toString().toInt(),previousActivity!!,false)
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId!!,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val savingsAndWishesDB= SavingsAndWishesDB.getInstance(application)
        val repository= SavingsAndWishesRepository(savingsAndWishesDB.savingsAndWishesDao())
        val savingsAndWishesViewModel by viewModels<SavingsAndWishesViewModel> {
            SavingsAndWishesViewModelFactory(repository,application)
        }


        if(layoutId==R.layout.layout_savingsrv) {
            val savingsRv =
                view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.savingsRv)
            savingsRv.layoutManager = LinearLayoutManager(application)
            val savingsAdapter = SavingsAdapter({savingsEntity-> onScroll(savingsEntity)})
            savingsRv.adapter = savingsAdapter

            savingsAndWishesViewModel.allSavings.observe(
                viewLifecycleOwner,
                Observer { allSavings ->
                    val allSavings1 = allSavings.map { requireNotNull(it) }
                    savingsAdapter.submitList(allSavings1)
                })
        }
        else if(layoutId==R.layout.layout_savingstotal)
        {
            val totalSavings1=view.findViewById<TextView>(R.id.savingTotal)
            savingsAndWishesViewModel.getTotalSavingsTransac()
            savingsAndWishesViewModel.totalSavings.observe(viewLifecycleOwner, Observer { totalSavings ->
                totalSavings?.let {
                    totalSavings1.text=it.toString()
                }
            })

        }

    }
    fun onScroll(savingsEntity: SavingsEntity)
    {
        saving=savingsEntity.deed
        sharedViewModel.counterSavingsScroll.value=sharedViewModel.counterSavingsScroll.value?.plus(1)
    }

    companion object
    {
        const val ARG_LAYOUT_ID="layoutId"
        const val ARG_PREVIOUS_ACTIVITY="Previous Activity"
        fun newInstance(layoutId:Int,application: Application,previousActivity:String?): SavingsFragment
        {
            val fragment = SavingsFragment(application)
            val args= Bundle()
            args.putInt(ARG_LAYOUT_ID,layoutId)
            args.putString(ARG_PREVIOUS_ACTIVITY,previousActivity)
            fragment.arguments=args
            return fragment
        }


    }
}