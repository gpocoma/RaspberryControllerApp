package com.galopocoma.raspberrycontrollerapp.models

data class StatsStatus(
    val message: String
    )

data class TransmissionStatus(
    val active: Boolean
    )

data class StartTransmission(
    val message: String
    )

data class StopTransmission(
    val message: String
    )

data class MinidlnaStatus(
    val active: Boolean
    )

data class StartMinidlna(
    val message: String
    )

data class StopMinidlna(
    val message: String
    )

data class CPUUsage(
    val cpuUsagePercent: Double
    )

data class RAMUsage(
    val totalMB: Double,
    val usedMB: Double,
    val freeMB: Double,
    val usedPercent: Double
    )
