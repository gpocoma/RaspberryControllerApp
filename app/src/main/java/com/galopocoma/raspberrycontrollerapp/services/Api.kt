package com.galopocoma.raspberrycontrollerapp.services

import com.galopocoma.raspberrycontrollerapp.models.StatsStatus
import com.galopocoma.raspberrycontrollerapp.models.TransmissionStatus
import com.galopocoma.raspberrycontrollerapp.models.StartTransmission
import com.galopocoma.raspberrycontrollerapp.models.StopTransmission
import com.galopocoma.raspberrycontrollerapp.models.MinidlnaStatus
import com.galopocoma.raspberrycontrollerapp.models.StartMinidlna
import com.galopocoma.raspberrycontrollerapp.models.StopMinidlna
import com.galopocoma.raspberrycontrollerapp.models.CPUUsage
import com.galopocoma.raspberrycontrollerapp.models.RAMUsage
import com.galopocoma.raspberrycontrollerapp.models.SystemShutdown
import com.galopocoma.raspberrycontrollerapp.models.SystemInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

interface RaspberryPiApi {
    @GET("system-monitor/")
    fun getServiceStatus(): Call<StatsStatus>

    @GET("system-monitor/cpu-usage")
    fun getCPUUsage(): Call<CPUUsage>

    @GET("system-monitor/ram-usage")
    fun getRAMUsage(): Call<RAMUsage>

    @GET("system-monitor/system-info")
    fun getSystemInfo(): Call<SystemInfo>

    @POST("system-monitor/shutdown")
    fun shutdown(): Call<SystemShutdown>

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