package com.org.flights

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@TestPropertySource(properties = [
    "spring.data.mongodb.database=flights_db",
    "spring.data.mongodb.port=0",
    "MONGO_DB_MAX_CONNECTION_IDLE_TIME=PT25M",
    "DATA_REFRESH_THRESHOLD_DURATION=PT30M",
    "CLEANUP_BATCH_SIZE_FOR_SELECTING_EXPIRED_USERS=100"
])
abstract class BaseIntegrationTest