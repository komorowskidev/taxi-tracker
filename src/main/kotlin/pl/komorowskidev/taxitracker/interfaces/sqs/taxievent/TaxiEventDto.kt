package pl.komorowskidev.taxitracker.interfaces.sqs.taxievent

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaxiEventDto(
    val taxiId: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val timestamp: Instant,
)
