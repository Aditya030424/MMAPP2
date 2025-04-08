package com.example.mmapp.Savings

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mmapp.Database.SavingsAndWishesDB
import com.example.mmapp.Database.SavingsAndWishesRepository
import com.example.mmapp.Database.SavingsAndWishesViewModel
import com.example.mmapp.Database.SavingsAndWishesViewModelFactory
import com.example.mmapp.R

class SavingsFragment(val application:Application): Fragment() {
    private var layoutId:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutId=it.getInt(ARG_LAYOUT_ID)
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
            val savingsAdapter = SavingsAdapter()
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

    companion object
    {
        const val ARG_LAYOUT_ID="layoutId"
        fun newInstance(layoutId:Int,application: Application): SavingsFragment
        {
            val fragment = SavingsFragment(application)
            val args= Bundle()
            args.putInt(ARG_LAYOUT_ID,layoutId)
            fragment.arguments=args
            return fragment
        }


    }
}