package com.example.mmapp.Networking2

import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mmapp.AHome.Constants
import com.example.mmapp.Counter.AppViewModelFactory
import com.example.mmapp.Counter.JsonLog
import com.example.mmapp.Counter.SharedViewModel
import com.example.mmapp.R
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class TypicodeActivity:AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_typicode)
        lateinit var message:JsonLog
        lateinit var jsonString:String
        val tag= Constants.TAG

        val previousActivity=intent.getStringExtra("Previous Activity")
        val gson = GsonBuilder().setPrettyPrinting().create()

        val postInfoButton=findViewById<Button>(R.id.postInfoButton)
        val getInfoButton=findViewById<Button>(R.id.getInfoButton)
        val typicodeTv=findViewById<TextView>(R.id.typicodeTv)

        val postViewModel:PostViewModel by viewModels()

        val appViewModelFactory = AppViewModelFactory(application)
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]

        postInfoButton.setOnClickListener {
            sharedViewModel.counterPostInfo.value=sharedViewModel.counterPostInfo.value?.plus(1)
            postViewModel.createPost(PostRequest(1, "New Title", "New Body"))
        }

        getInfoButton.setOnClickListener {
            sharedViewModel.counterGetInfo.value=sharedViewModel.counterGetInfo.value?.plus(1)
            postViewModel.getPosts()
        }

        postViewModel.gets.observe(this) { response ->
            typicodeTv.text = response.toString()
        }

        postViewModel.posts.observe(this) { response ->
            typicodeTv.text = response.toString()
            Log.d("Response","Textview updated")
        }

        sharedViewModel.counterTypicode.observe(this) {
            if (it > 0) {
                message= JsonLog("Typicode Activity Opened",it.toString().toInt(),previousActivity!!)
                jsonString = gson.toJson(message)
                Log.d(tag,jsonString)
            }
        }
        sharedViewModel.counterPostInfo.observe(this) {
            if (it > 0) {
                message= JsonLog("PostInfo Button pressed",it.toString().toInt(),previousActivity!!,false,"POST")
                jsonString = gson.toJson(message)
                Log.d(tag,jsonString)
            }
        }
        sharedViewModel.counterGetInfo.observe(this) {
            if (it > 0) {
                message= JsonLog("GetInfo Button pressed",it.toString().toInt(),previousActivity!!,false,"GET")
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
    }



    override fun onResume()
    {
        super.onResume()
        sharedViewModel.counterTypicode.value=sharedViewModel.counterTypicode.value?.plus(1)
    }

}
