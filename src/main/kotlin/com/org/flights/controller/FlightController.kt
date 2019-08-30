package com.org.flights.controller

import com.org.flights.model.Flight
import com.org.flights.service.FlightService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/flights")
class FlightController(private val flightService: FlightService) {

    // Get All Flights
    @CrossOrigin
    @GetMapping
    fun getAllFlights(): ResponseEntity<Flux<Flight>> {
        return ResponseEntity.ok(flightService.findAllFlights())
    }

    // Get a Single Flight
    @CrossOrigin
    @GetMapping("/{id}")
    fun getFlightById(@PathVariable(value = "id") flightId: String): Mono<ResponseEntity<Flight>> {
        return flightService.findFlightById(flightId)
                .map { foundFlight -> ResponseEntity.ok(foundFlight) }
                .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @CrossOrigin
    @PostMapping
    fun addNewFlight(@RequestBody newFlight: Flight): Mono<Flight> {
        return flightService.saveNewFlight(newFlight)
    }
}