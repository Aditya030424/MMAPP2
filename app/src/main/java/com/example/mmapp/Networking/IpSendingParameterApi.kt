package com.example.mmapp.Networking

import retrofit2.http.GET
import retrofit2.http.Path

interface IpSendingParameterApi {
    @GET("/{ip1}/geo")
    suspend fun sendIp(@Path("ip1") ip1: String):Geo
}