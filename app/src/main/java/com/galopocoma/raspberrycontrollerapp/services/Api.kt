package com.galopocoma.raspberrycontrollerapp.services

import com.galopocoma.raspberrycontrollerapp.models.StatsStatus
import com.galopocoma.raspberrycontrollerapp.models.TransmissionStatus
import com.galopocoma.raspberrycontrollerapp.models.StartTransmission
import com.galopocoma.raspberrycontrollerapp.models.StopTransmission
import com.galopocoma.raspberrycontrollerapp.models.MinidlnaStatus
import com.galopocoma.raspberrycontrollerapp.models.StartMinidlna
import com.galopocoma.raspberrycontrollerapp.models.StopMinidlna

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

interface RaspberryPiApi {
    @GET("stats/")
    fun getServiceStatus(): Call<StatsStatus>

    @GET("transmission/")
    fun getTransmissionStatus(): Call<TransmissionStatus>

    @POST("transmission/start")
    fun startTransmission(): Call<StartTransmission>

    @POST("transmission/stop")
    fun stopTransmission(): Call<StopTransmission>

    @GET("minidlna/")
    fun getMinidlnaStatus(): Call<MinidlnaStatus>

    @POST("minidlna/start")
    fun startMinidlna(): Call<StartMinidlna>

    @POST("minidlna/stop")
    fun stopMinidlna(): Call<StopMinidlna>
}

object RetrofitClient {
    private const val BASE_URL = "http://192.178.0.114:3000/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RaspberryPiApi by lazy {
        instance.create(RaspberryPiApi::class.java)
    }
}