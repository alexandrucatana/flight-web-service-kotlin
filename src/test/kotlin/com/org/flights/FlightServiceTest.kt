package com.org.flights

import com.nhaarman.mockitokotlin2.*
import com.org.flights.model.Flight
import com.org.flights.model.FlightEntity
import com.org.flights.repository.FlightRepository
import com.org.flights.service.FlightService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightServiceTest {
    private val flightRepositiory = mock<FlightRepository>()
    private val flightService = FlightService(flightRepositiory)
    private var flightList = MutableList(2) { FlightEntity(Flight()) }

    @BeforeAll
    fun init() {
        flightList.clear()
        flightList.add(FlightEntity(Flight("8c5bf5f8-b394-11e9-a2a3-2a2ae2dbcce4", "BlueAir", "Madrid", "Tokyo")))
        flightList.add(FlightEntity(Flight("8c5bf9ea-b394-11e9-a2a3-2a2ae2dbcce4", "Lufthansa", "Bucharest", "Stuttgart")))
    }

    @Test
    fun whenValidId_thenFlightShouldBeFound() {
        val blueAir = flightList[0]
        val givenId  = "8c5bf5f8-b394-11e9-a2a3-2a2ae2dbcce4"

        whenever(flightRepositiory.findFlightByAirlineId(givenId)).thenReturn(Mono.just(blueAir))
        val foundFlight = flightService.findFlightById(givenId)
        StepVerifier.create(foundFlight).assertNext{
            assertThat(blueAir.data.airlineId).isEqualTo(it.airlineId)
            assertThat(blueAir.data.airlineName).isEqualTo(it.airlineName)
            assertThat(blueAir.data.start).isEqualTo(it.start)
            assertThat(blueAir.data.destination).isEqualTo(it.destination)
        }.verifyComplete()

        verify(flightRepositiory, times(1)).findFlightByAirlineId(givenId)
    }

    @Test
    fun whenValidList_thenAllFlightsShouldBeFound() {
        val fluxList : Flux<FlightEntity> = flightList.toFlux()
        whenever(flightRepositiory.findAll()).thenReturn(fluxList)
        val foundFlights = flightService.findAllFlights()

        StepVerifier.create(foundFlights).assertNext{
            assertThat(flightList[0].data.airlineId).isEqualTo(it.airlineId)
            assertThat(flightList[0].data.airlineName).isEqualTo(it.airlineName)
            assertThat(flightList[0].data.start).isEqualTo(it.start)
            assertThat(flightList[0].data.destination).isEqualTo(it.destination)
        }.assertNext{
            assertThat(flightList[1].data.airlineId).isEqualTo(it.airlineId)
            assertThat(flightList[1].data.airlineName).isEqualTo(it.airlineName)
            assertThat(flightList[1].data.start).isEqualTo(it.start)
            assertThat(flightList[1].data.destination).isEqualTo(it.destination)
        }.verifyComplete()
    }





}