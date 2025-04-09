package com.example.mmapp.Networking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mmapp.R

class BasicNetworkingFragment:Fragment() {
    private var layoutId:Int?=null
    private var output:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutId=it.getInt(ARG_LAYOUT_ID)
            output=it.getString(ARG_OUTPUT)
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
        val bnTv=view.findViewById<TextView>(R.id.bnTv)
        bnTv.text=output

    }
    companion object{
        private const val ARG_LAYOUT_ID="layoutId"
        private const val ARG_OUTPUT="output"
        fun newInstance(layoutId:Int,output:String):BasicNetworkingFragment
        {
            val fragment=BasicNetworkingFragment()
            val args= Bundle()
            args.putInt(ARG_LAYOUT_ID,layoutId)
            args.putString(ARG_OUTPUT,output)
            fragment.arguments=args
            return fragment
        }

    }
}