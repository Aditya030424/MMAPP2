package com.example.mmapp.Networking2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mmapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypicodeFragment:Fragment() {
    private var layoutId:Int?=null
    private var output:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutId = it.getInt(ARG_LAYOUT_ID)
            output = it.getString(ARG_OUTPUT)
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
        if(layoutId==R.layout.layout_getinfo)
        {
            val getInfoTv=view.findViewById<TextView>(R.id.getInfoTv)
            getInfoTv.text=output
        }
        if(layoutId==R.layout.layout_postinfo)
        {
            val postInfoTv=view.findViewById<TextView>(R.id.postInfoTv)
            postInfoTv.text=output
        }
    }
    companion object{
        const val ARG_LAYOUT_ID="layoutId"
        const val ARG_OUTPUT="output"
        fun newInstance(layoutId:Int,output:String):TypicodeFragment {
        val fragment = TypicodeFragment()
        val args = Bundle()
        args.putInt(ARG_LAYOUT_ID, layoutId)
        args.putString(ARG_OUTPUT, output)
        fragment.arguments = args
        return fragment
        }
    }
}