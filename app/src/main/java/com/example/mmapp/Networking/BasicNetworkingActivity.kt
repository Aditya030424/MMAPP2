package com.example.mmapp.Networking

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mmapp.AHome.Constants
import com.example.mmapp.Counter.AppViewModelFactory
import com.example.mmapp.Counter.JsonLog
import com.example.mmapp.Counter.SharedViewModel
import com.example.mmapp.R
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java


class BasicNetworkingActivity:AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_basicnetworking)
        val tag= Constants.TAG
        val previousActivity=intent.getStringExtra("Previous Activity")
        val gson = GsonBuilder().setPrettyPrinting().create()

        lateinit var message:JsonLog
        lateinit var jsonString:String

        var geo:String?
        var ip1:String?=""

        val retrofit= Retrofit.Builder().baseUrl("https://api.ipify.org").addConverterFactory(GsonConverterFactory.create()).build()
        val IpAcquiringApi=retrofit.create(IpAcquiringApi::class.java)


        val getIpButton1=findViewById<Button>(R.id.getIpButton1)
        val sendIpButton=findViewById<Button>(R.id.sendIpButton)
        val bnTv1=findViewById<TextView>(R.id.bnTv1)

        getIpButton1.setOnClickListener {
            sharedViewModel.counterGetIpButton1.value=sharedViewModel.counterGetIpButton1.value?.plus(1)
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    ip1 = IpAcquiringApi.getIp().ip
                } catch (e: Exception) {
                    ip1 = e.message
                }
                withContext(Dispatchers.Main) {
                    bnTv1.text = ip1
                }
            }
        }

        sendIpButton.setOnClickListener {
            sharedViewModel.counterSendIpButton.value=sharedViewModel.counterSendIpButton.value?.plus(1)
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val retrofit2=Retrofit.Builder().baseUrl("https://ipinfo.io").addConverterFactory(GsonConverterFactory.create()).build()
                    val IpSendingApi=retrofit2.create(IpSendingParameterApi::class.java)
                    geo = IpSendingApi.sendIp(ip1!!).toString()
                }
                catch (e:Exception)
                {
                    geo=e.message
                }
                
                withContext(Dispatchers.Main) {
                    bnTv1.text = geo
                }
            }
        }

        val appViewModelFactory = AppViewModelFactory(application)
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]

        sharedViewModel.counterBNA.observe(this) {
            if (it > 0) {
                message= JsonLog("BNA Opened",it.toString().toInt(),previousActivity!!,)
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }

        sharedViewModel.counterGetIpButton1.observe(this) {
            if (it > 0) {
                message= JsonLog("GetIp Button pressed",it.toString().toInt(),previousActivity!!,false,"GET")
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }

        sharedViewModel.counterSendIpButton.observe(this) {
            if (it > 0) {
                message= JsonLog("SendIp Button pressed",it.toString().toInt(),previousActivity!!,false,"GET")
                jsonString = gson.toJson(message)
                Log.d(tag, jsonString)
            }
        }
    }


    override fun onResume()
    {
        super.onResume()
        sharedViewModel.counterBNA.value=sharedViewModel.counterBNA.value?.plus(1)
    }

}