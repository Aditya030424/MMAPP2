package com.example.mmapp.Networking2

import android.os.Bundle
import android.util.Log

import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
        val appViewModelFactory = AppViewModelFactory(application)
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]
        val postInfoButton=findViewById<Button>(R.id.postInfoButton)
        val getInfoButton=findViewById<Button>(R.id.getInfoButton)
        val previousActivity=intent.getStringExtra("Previous Activity")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val postViewModel:PostViewModel by viewModels()
        sharedViewModel.counterTypicode.observe(this) {
            if (it > 0) {
                val message= JsonLog("Typicode Activity Opened",it.toString().toInt(),previousActivity!!)
                val jsonString = gson.toJson(message)
                Log.d("Tracking",jsonString)
            }
        }
        sharedViewModel.counterPostInfo.observe(this) {
            if (it > 0) {
                val message= JsonLog("PostInfo Button pressed",it.toString().toInt(),previousActivity!!,false,"POST")
                val jsonString = gson.toJson(message)
                Log.d("Tracking",jsonString)
            }
        }
        sharedViewModel.counterGetInfo.observe(this) {
            if (it > 0) {
                val message= JsonLog("GetInfo Button pressed",it.toString().toInt(),previousActivity!!,false,"GET")
                val jsonString = gson.toJson(message)
                Log.d("Tracking", jsonString)
            }
        }
        postViewModel.posts.observe(this) { response ->
            supportFragmentManager.beginTransaction().replace(R.id.typicodeFragmentContainer,TypicodeFragment.newInstance(R.layout.layout_postinfo,response.toString()))
                .commit()
            Log.d("Response","Textview updated")
        }
        postInfoButton.setOnClickListener {
            sharedViewModel.counterPostInfo.value=sharedViewModel.counterPostInfo.value?.plus(1)
            postViewModel.createPost(PostRequest(1, "New Title", "New Body"))
        }
        postViewModel.gets.observe(this) { response ->
            supportFragmentManager.beginTransaction().replace(R.id.typicodeFragmentContainer,TypicodeFragment.newInstance(R.layout.layout_getinfo,response.toString()))
                .commit()
        }
        getInfoButton.setOnClickListener {
            sharedViewModel.counterGetInfo.value=sharedViewModel.counterGetInfo.value?.plus(1)
            postViewModel.getPosts()
        }
    }
    override fun onResume()
    {
        super.onResume()
        sharedViewModel.counterTypicode.value=sharedViewModel.counterTypicode.value?.plus(1)
    }

}
