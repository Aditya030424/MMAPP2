package com.example.mmapp.Wishes

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

class WishesFragment(val application: Application):Fragment() {
    private var layoutId: Int? = null
    private var previousActivity:String?=null
    private lateinit var sharedViewModel: SharedViewModel

    val savingsAndWishesDB= SavingsAndWishesDB.getInstance(application)
    val repository= SavingsAndWishesRepository(savingsAndWishesDB.savingsAndWishesDao())
    val savingsAndWishesViewModel by viewModels<SavingsAndWishesViewModel> {
        SavingsAndWishesViewModelFactory(repository,application)
    }

    val wishesAdapter= WishesAdapter({wishesEntity -> onItemClicked(wishesEntity) },{wishesEntity ->onItemClicked1(wishesEntity)})


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutId=it.getInt(ARG_LAYOUT_ID_WISHES)
            previousActivity=it.getString(ARG_PREVIOUS_ACTIVITY)
        }

        val tag= Constants.TAG
        val gson=GsonBuilder().setPrettyPrinting().create()
        val appViewModelFactory = AppViewModelFactory(application)
        lateinit var message:JsonLog
        lateinit var jsonString:String

        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]
        sharedViewModel.counterCheckBox.observe(this) {
            if (it > 0) {
                message= JsonLog("CheckBox pressed",it.toString().toInt(),previousActivity!!,true)
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
        sharedViewModel.counterPrefButton.observe(this) {
            if (it > 0) {
                message= JsonLog("Pref Button pressed",it.toString().toInt(),previousActivity!!,true)
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
    }

    fun clearChecked()
    {
        val list1=savingsAndWishesViewModel.allWishes.value?.filter { !it.checked }
        wishesAdapter.submitList(list1)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId!!,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(layoutId== R.layout.layout_wishesrv)
        {
            val wishesRv=view.findViewById<RecyclerView>(R.id.wishesRv)


            wishesRv.layoutManager=LinearLayoutManager(application)
            wishesRv.adapter=wishesAdapter

            savingsAndWishesViewModel.allWishes.observe(viewLifecycleOwner) { allWishes ->
                val allWishes1 = allWishes.map { requireNotNull(it) }
                val wishesSortByPref=allWishes1.sortedByDescending { it.pref }
                wishesAdapter.submitList(wishesSortByPref)
            }

        }
        else if(layoutId==R.layout.layout_wishestotal)
        {
            savingsAndWishesViewModel.getTotalWishesTransac()
            val totalWishes1=view.findViewById<TextView>(R.id.wishesTotal)
            savingsAndWishesViewModel.totalWishes.observe(viewLifecycleOwner) { totalWishes ->
                totalWishes?.let {
                    totalWishes1.text=it.toString()
                }
            }
        }
    }

    fun onItemClicked(wishesEntity: WishesEntity) {
        sharedViewModel.counterCheckBox.value = sharedViewModel.counterCheckBox.value?.plus(1)
        if(wishesEntity.checked)
        {
            savingsAndWishesViewModel.updateCheckedTo0(wishesEntity)
        }
        else {
            savingsAndWishesViewModel.updateCheckedTo1(wishesEntity)
        }
    }

    fun onItemClicked1(wishesEntity: WishesEntity) {
        sharedViewModel.counterPrefButton.value = sharedViewModel.counterPrefButton.value?.plus(1)
        savingsAndWishesViewModel.incrementPref(wishesEntity)

    }

    companion object {
        private const val ARG_LAYOUT_ID_WISHES = "layoutId"
        private const val ARG_PREVIOUS_ACTIVITY="Previous Activity"
        fun newInstance(layoutId: Int, application: Application,previousActivity:String?): WishesFragment {
            val fragment = WishesFragment(application)
            val args = Bundle()
            args.putInt(ARG_LAYOUT_ID_WISHES, layoutId)
            args.putString(ARG_PREVIOUS_ACTIVITY,previousActivity)
            fragment.arguments = args
            return fragment
        }
    }
}