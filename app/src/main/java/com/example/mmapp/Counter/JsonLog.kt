package com.example.mmapp.Counter

data class JsonLog(val action: String, val impression: Int, val previousActivity: String,var databaseOp:Boolean=false,var apiCall:String="No API call")
