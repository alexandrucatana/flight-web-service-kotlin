package com.org.flights.model

import java.util.*

data class Flight (
        val airlineId: String = UUID.randomUUID().toString(),

        val airlineName: String = "",

        val start: String = "",

        val destination: String = ""
)