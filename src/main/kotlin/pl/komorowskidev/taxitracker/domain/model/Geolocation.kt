package pl.komorowskidev.taxitracker.domain.model

import java.math.BigDecimal

data class Geolocation(
    val latitude: BigDecimal,
    val longitude: BigDecimal,
)
