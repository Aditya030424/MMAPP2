package com.example.mmapp.Networking

import retrofit2.http.GET

interface IpAcquiringApi {
    @GET("/?format=json")
    suspend fun getIp():Ip
}