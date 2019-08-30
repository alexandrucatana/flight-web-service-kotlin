package com.org.flights.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "flight")
open class FlightEntity(
        data : Flight
) : BaseEntity<Flight>(data) {

    fun toFlight(): Flight {
        return Flight(
                this.data.airlineId,
                this.data.airlineName,
                this.data.start,
                this.data.destination
        )
    }
}