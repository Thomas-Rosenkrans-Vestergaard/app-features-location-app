package com.example.locationfeature

import android.location.Criteria

fun createCoarseCriteria(): Criteria {

    val c = Criteria()
    c.accuracy = Criteria.ACCURACY_COARSE
    c.isAltitudeRequired = false
    c.isBearingRequired = false
    c.isSpeedRequired = false
    c.isCostAllowed = true
    c.powerRequirement = Criteria.POWER_HIGH
    return c
}

fun createFineCriteria(): Criteria {

    val c = Criteria()
    c.accuracy = Criteria.ACCURACY_FINE
    c.isAltitudeRequired = false
    c.isBearingRequired = false
    c.isSpeedRequired = false
    c.isCostAllowed = true
    c.powerRequirement = Criteria.POWER_HIGH
    return c
}