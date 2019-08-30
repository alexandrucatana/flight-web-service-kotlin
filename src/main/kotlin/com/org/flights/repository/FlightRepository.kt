package com.org.flights.repository

import com.org.flights.model.FlightEntity
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface FlightRepository: ReactiveMongoRepository<FlightEntity, String>{

    @Query("{'data.airlineId' : ?0}")
    fun  findFlightByAirlineId(id:String) : Mono<FlightEntity>
}