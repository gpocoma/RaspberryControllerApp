package com.galopocoma.raspberrycontrollerapp.controllers

import com.galopocoma.raspberrycontrollerapp.models.StatsStatus
import com.galopocoma.raspberrycontrollerapp.models.CPUUsage
import com.galopocoma.raspberrycontrollerapp.models.RAMUsage
import com.galopocoma.raspberrycontrollerapp.models.SystemShutdown
import com.galopocoma.raspberrycontrollerapp.models.SystemInfo

import com.galopocoma.raspberrycontrollerapp.models.TransmissionStatus
import com.galopocoma.raspberrycontrollerapp.models.StartTransmission
import com.galopocoma.raspberrycontrollerapp.models.StopTransmission

import com.galopocoma.raspberrycontrollerapp.models.MinidlnaStatus
import com.galopocoma.raspberrycontrollerapp.models.StartMinidlna
import com.galopocoma.raspberrycontrollerapp.models.StopMinidlna

import com.galopocoma.raspberrycontrollerapp.models.PostgreSQLStatus
import com.galopocoma.raspberrycontrollerapp.models.StartPostgreSQL
import com.galopocoma.raspberrycontrollerapp.models.StopPostgreSQL


import com.galopocoma.raspberrycontrollerapp.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ServiceStatusCallback {
    fun onSuccess(serviceStatus: StatsStatus?)
    fun onError(message: String)
}

interface TransmissionStatusCallback {
    fun onSuccess(transmissionStatus: TransmissionStatus?)
    fun onError(message: String)
}

interface StartTransmissionCallback {
    fun onSuccess(startTransmission: StartTransmission?)
    fun onError(message: String)
}

interface StopTransmissionCallback {
    fun onSuccess(stopTransmission: StopTransmission?)
    fun onError(message: String)
}

interface MinidlnaStatusCallback {
    fun onSuccess(minidlnaStatus: MinidlnaStatus?)
    fun onError(message: String)
}

interface StartMinidlnaCallback {
    fun onSuccess(startMinidlna: StartMinidlna?)
    fun onError(message: String)
}

interface StopMinidlnaCallback {
    fun onSuccess(stopMinidlna: StopMinidlna?)
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

interface SystemShutdownCallback {
    fun onSuccess(systemShutdown: SystemShutdown)
    fun onError(message: String)
}

interface SystemInfoCallback {
    fun onSuccess(systemInfo: SystemInfo)
    fun onError(message: String)
}

interface PostgreSQLStatusCallback {
    fun onSuccess(postgreSQLStatus: PostgreSQLStatus?)
    fun onError(message: String)
}

interface StartPostgreSQLCallback {
    fun onSuccess(startPostgreSQL: StartPostgreSQL?)
    fun onError(message: String)
}

interface StopPostgreSQLCallback {
    fun onSuccess(stopPostgreSQL: StopPostgreSQL?)
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

    fun fetchSystemInfo(callback: SystemInfoCallback) {
        val call = RetrofitClient.api.getSystemInfo()
        call.enqueue(object : Callback<SystemInfo> {
            override fun onResponse(call: Call<SystemInfo>, response: Response<SystemInfo>) {
                if (response.isSuccessful) {
                    val systemInfo = response.body()
                    callback.onSuccess(systemInfo!!)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SystemInfo>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun shutdown(callback: SystemShutdownCallback) {
        val call = RetrofitClient.api.shutdown()
        call.enqueue(object : Callback<SystemShutdown> {
            override fun onResponse(call: Call<SystemShutdown>, response: Response<SystemShutdown>) {
                if (response.isSuccessful) {
                    val systemShutdown = response.body()
                    callback.onSuccess(systemShutdown!!)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SystemShutdown>, t: Throwable) {
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

    fun fetchPostgreSQLStatus(callback: PostgreSQLStatusCallback) {
        val call = RetrofitClient.api.getPostgreSQLStatus()
        call.enqueue(object : Callback<PostgreSQLStatus> {
            override fun onResponse(call: Call<PostgreSQLStatus>, response: Response<PostgreSQLStatus>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostgreSQLStatus>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun startPostgreSQL(callback: StartPostgreSQLCallback) {
        val call = RetrofitClient.api.startPostgreSQL()
        call.enqueue(object : Callback<StartPostgreSQL> {
            override fun onResponse(call: Call<StartPostgreSQL>, response: Response<StartPostgreSQL>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StartPostgreSQL>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }

    fun stopPostgreSQL(callback: StopPostgreSQLCallback) {
        val call = RetrofitClient.api.stopPostgreSQL()
        call.enqueue(object : Callback<StopPostgreSQL> {
            override fun onResponse(call: Call<StopPostgreSQL>, response: Response<StopPostgreSQL>) {
                if (response.isSuccessful) {
                    val serviceStatus = response.body()
                    callback.onSuccess(serviceStatus)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StopPostgreSQL>, t: Throwable) {
                callback.onError("Failure: ${t.message}")
            }
        })
    }
}