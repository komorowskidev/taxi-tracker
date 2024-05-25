package pl.komorowskidev.taxitracker.domain.model

import java.math.BigDecimal
import java.time.Instant

data class Taxi(
    val id: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val timestamp: Instant,
)
