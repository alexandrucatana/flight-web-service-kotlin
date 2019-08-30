package com.org.flights.service

import com.org.flights.model.Flight
import com.org.flights.model.FlightEntity
import com.org.flights.repository.FlightRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class FlightService (private val flightRepository: FlightRepository) {
    fun findAllFlights(): Flux<Flight> = flightRepository.findAll().map { it.toFlight() }
    fun findFlightById(id: String): Mono<Flight> = flightRepository.findFlightByAirlineId(id).map { it.toFlight() }
    fun saveNewFlight(flight: Flight) : Mono<Flight> = flightRepository.save(FlightEntity(flight)).map { it.toFlight() }
}