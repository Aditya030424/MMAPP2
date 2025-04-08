package com.example.mmapp.Networking

import android.os.Bundle
import android.util.Log
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
        var geo:String?=""
        val retrofit= Retrofit.Builder().baseUrl("https://api.ipify.org").addConverterFactory(GsonConverterFactory.create()).build()
        val IpAcquiringApi=retrofit.create(IpAcquiringApi::class.java)
        var ip1:String?=""
        val getIpButton1=findViewById<Button>(R.id.getIpButton1)
        val sendIpButton=findViewById<Button>(R.id.sendIpButton)
        val appViewModelFactory = AppViewModelFactory(application)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val previousActivity=intent.getStringExtra("Previous Activity")
        sharedViewModel = ViewModelProvider(this, appViewModelFactory)[SharedViewModel::class.java]
        supportFragmentManager.beginTransaction().add(R.id.bnFragment,BasicNetworkingFragment.newInstance(R.layout.layout_basicnetworkingfragment,"")).commit()
        sharedViewModel.counterBNA.observe(this) {
            if (it > 0) {
                val message= JsonLog("BNA Opened",it.toString().toInt(),previousActivity!!,)
                val jsonString = gson.toJson(message)

                Log.d("Tracking", jsonString)
            }
        }
        sharedViewModel.countergetIpButton1.observe(this) {
            if (it > 0) {
                val message= JsonLog("GetIp Button pressed",it.toString().toInt(),previousActivity!!,false,"GET")
                val jsonString = gson.toJson(message)
                Log.d("Tracking", jsonString)


        }
            }
        sharedViewModel.counterSendIpButton.observe(this) {
            if (it > 0) {
                val message= JsonLog("SendIp Button pressed",it.toString().toInt(),previousActivity!!,false,"GET")
                val jsonString = gson.toJson(message)
                Log.d("Tracking", jsonString)
            }
        }
        getIpButton1.setOnClickListener {
            sharedViewModel.countergetIpButton1.value=sharedViewModel.countergetIpButton1.value?.plus(1)
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    ip1 = IpAcquiringApi.getIp().ip
                } catch (e: Exception) {
                    ip1 = e.message
                }
                withContext(Dispatchers.Main) {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.bnFragment,
                            BasicNetworkingFragment.newInstance(
                                R.layout.layout_basicnetworkingfragment,
                                ip1!!
                            )
                        ).commit()
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
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.bnFragment,
                            BasicNetworkingFragment.newInstance(
                                R.layout.layout_basicnetworkingfragment,
                                geo!!
                            )
                        ).commit()
                }
            }
        }
    }
    override fun onResume()
    {
        super.onResume()
        sharedViewModel.counterBNA.value=sharedViewModel.counterBNA.value?.plus(1)
    }

}