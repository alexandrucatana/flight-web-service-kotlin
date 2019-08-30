package com.org.flights.model

import org.springframework.data.annotation.Id
import java.time.Instant

abstract class BaseEntity<T>(
        var data: T
) {

    @Id
    lateinit var id: String

    lateinit var lastModified: Instant

    fun setLastModifiedToCurrentInstant() {
        this.lastModified = Instant.now()
    }
}