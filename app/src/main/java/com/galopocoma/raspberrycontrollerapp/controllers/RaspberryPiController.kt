package com.galopocoma.raspberrycontrollerapp.controllers

import com.galopocoma.raspberrycontrollerapp.models.StatsStatus
import com.galopocoma.raspberrycontrollerapp.models.CPUUsage
import com.galopocoma.raspberrycontrollerapp.models.RAMUsage
import com.galopocoma.raspberrycontrollerapp.models.TransmissionStatus
import com.galopocoma.raspberrycontrollerapp.models.StartTransmission
import com.galopocoma.raspberrycontrollerapp.models.StopTransmission

import com.galopocoma.raspberrycontrollerapp.models.MinidlnaStatus
import com.galopocoma.raspberrycontrollerapp.models.StartMinidlna
import com.galopocoma.raspberrycontrollerapp.models.StopMinidlna
import com.galopocoma.raspberrycontrollerapp.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ServiceStatusCallback {
    fun onSuccess(serviceStatus: StatsStatus?)
    fun onError(message: String)
}

interface TransmissionStatusCallback {
    fun onSuccess(serviceStatus: TransmissionStatus?)
    fun onError(message: String)
}

interface StartTransmissionCallback {
    fun onSuccess(serviceStatus: StartTransmission?)
    fun onError(message: String)
}

interface StopTransmissionCallback {
    fun onSuccess(serviceStatus: StopTransmission?)
    fun onError(message: String)
}

interface MinidlnaStatusCallback {
    fun onSuccess(serviceStatus: MinidlnaStatus?)
    fun onError(message: String)
}

interface StartMinidlnaCallback {
    fun onSuccess(serviceStatus: StartMinidlna?)
    fun onError(message: String)
}

interface StopMinidlnaCallback {
    fun onSuccess(serviceStatus: StopMinidlna?)
    fun onError(message: String)
}

interface CPUUsageCallback {
    fun onSuccess(cpuUsage: CPUUsage)
    fun onError(message: String)
}

interface RAMUsageCallback {
    fun onSuccess(ramUsage: RAMUsage)
    fun onError(message: String)
}

class RaspberryPiController {

    fun fetchServiceStatus(callback: ServiceStatusCallback) {
        val call = RetrofitClient.api.getServiceStatus()
        call.enqueue(object : Callback<StatsStatus> {
            override fun onResponse(call: Call<StatsStatus>, response: Response<StatsStatus>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StatsStatus>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun fetchCPUUsage(callback: CPUUsageCallback) {
        val call = RetrofitClient.api.getCPUUsage()
        call.enqueue(object : Callback<CPUUsage> {
            override fun onResponse(call: Call<CPUUsage>, response: Response<CPUUsage>) {
                if (response.isSuccessful) {
                    val cpuUsage = response.body()
                    callback.onSuccess(cpuUsage!!)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CPUUsage>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun fetchRAMUsage(callback: RAMUsageCallback) {
        val call = RetrofitClient.api.getRAMUsage()
        call.enqueue(object : Callback<RAMUsage> {
            override fun onResponse(call: Call<RAMUsage>, response: Response<RAMUsage>) {
                if (response.isSuccessful) {
                    val ramUsage = response.body()
                    callback.onSuccess(ramUsage!!)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RAMUsage>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun fetchTransmissionStatus(callback: TransmissionStatusCallback) {
        val call = RetrofitClient.api.getTransmissionStatus()
        call.enqueue(object : Callback<TransmissionStatus> {
            override fun onResponse(call: Call<TransmissionStatus>, response: Response<TransmissionStatus>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TransmissionStatus>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun startTransmission(callback: StartTransmissionCallback) {
        val call = RetrofitClient.api.startTransmission()
        call.enqueue(object : Callback<StartTransmission> {
            override fun onResponse(call: Call<StartTransmission>, response: Response<StartTransmission>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StartTransmission>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun stopTransmission(callback: StopTransmissionCallback) {
        val call = RetrofitClient.api.stopTransmission()
        call.enqueue(object : Callback<StopTransmission> {
            override fun onResponse(call: Call<StopTransmission>, response: Response<StopTransmission>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StopTransmission>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun fetchMinidlnaStatus(callback: MinidlnaStatusCallback) {
        val call = RetrofitClient.api.getMinidlnaStatus()
        call.enqueue(object : Callback<MinidlnaStatus> {
            override fun onResponse(call: Call<MinidlnaStatus>, response: Response<MinidlnaStatus>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MinidlnaStatus>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun startMinidlna(callback: StartMinidlnaCallback) {
        val call = RetrofitClient.api.startMinidlna()
        call.enqueue(object : Callback<StartMinidlna> {
            override fun onResponse(call: Call<StartMinidlna>, response: Response<StartMinidlna>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StartMinidlna>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun stopMinidlna(callback: StopMinidlnaCallback) {
        val call = RetrofitClient.api.stopMinidlna()
        call.enqueue(object : Callback<StopMinidlna> {
            override fun onResponse(call: Call<StopMinidlna>, response: Response<StopMinidlna>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StopMinidlna>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }
}