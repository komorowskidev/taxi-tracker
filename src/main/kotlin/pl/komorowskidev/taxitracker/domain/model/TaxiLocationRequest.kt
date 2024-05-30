package pl.komorowskidev.taxitracker.domain.model

import java.time.Instant

data class TaxiLocationRequest(
    val taxiId: String,
    val startTime: Instant,
    val endTime: Instant,
)
