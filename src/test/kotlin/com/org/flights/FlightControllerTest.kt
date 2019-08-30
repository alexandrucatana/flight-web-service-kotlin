package com.org.flights

import com.org.flights.model.Flight
import com.org.flights.model.FlightEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.web.reactive.server.WebTestClient

@EnableAutoConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightControllerTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    private var blueAirFlight = FlightEntity(Flight("8c5bf5f8-b394-11e9-a2a3-2a2ae2dbcce4", "BlueAir", "Madrid", "Tokyo"))
    private var lufthansaFlight = FlightEntity(Flight("8c5bf9ea-b394-11e9-a2a3-2a2ae2dbcce4", "Lufthansa", "Bucharest", "Stuttgart"))

    @BeforeEach
    fun init() {
        mongoTemplate.findAllAndRemove(Query(), FlightEntity::class.java)
        mongoTemplate.save(blueAirFlight)
        mongoTemplate.save(lufthansaFlight)
    }

    @Test
    fun find_flightId_OK() {
        webTestClient.get().uri("v1/flights/8c5bf5f8-b394-11e9-a2a3-2a2ae2dbcce4")
                .exchange()
                .expectStatus().isOk
                .expectBody().json(getTestFlight())
    }

    @Test
    fun find_flightId_NotFound() {
        webTestClient.get().uri("/v1/flights/3")
                .exchange()
                .expectStatus().isNotFound
    }

    @Test
    fun find_allFlights_OK() {
        webTestClient.get().uri("/v1/flights")
                .exchange()
                .expectStatus().isOk
                .expectBody().json(getAllTestFlights())
    }

    private fun getTestFlight() = """
    {
        "airlineId":"8c5bf5f8-b394-11e9-a2a3-2a2ae2dbcce4",
        "airlineName":"BlueAir",
        "start":"Madrid",
        "destination":"Tokyo"        
    }
    """.trimIndent()

    private fun getAllTestFlights() = """
    [
        {
            "airlineId":"8c5bf5f8-b394-11e9-a2a3-2a2ae2dbcce4",
            "airlineName":"BlueAir",
            "start":"Madrid",
            "destination":"Tokyo"            
        },
        {
            "airlineId":"8c5bf9ea-b394-11e9-a2a3-2a2ae2dbcce4",
            "airlineName":"Lufthansa",
            "start":"Bucharest",
            "destination":"Stuttgart"            
        }
    ]
    """.trimIndent()
}