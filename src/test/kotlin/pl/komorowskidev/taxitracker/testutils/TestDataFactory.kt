package pl.komorowskidev.taxitracker.testutils

import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import pl.komorowskidev.taxitracker.infrastructure.mongodb.TaxiLocationEntity
import pl.komorowskidev.taxitracker.interfaces.rest.taxilocation.TaxiLocationDto
import java.math.BigDecimal
import java.time.Instant

class TestDataFactory {
    fun createTaxiLocation(
        taxiId: String = "taxiId",
        latitude: BigDecimal = BigDecimal(1.0),
        longitude: BigDecimal = BigDecimal(2.0),
        timestamp: Instant = Instant.parse("2023-04-20T10:15:30Z"),
    ) = TaxiLocation(
        id = 1L,
        taxiId = taxiId,
        latitude = latitude,
        longitude = longitude,
        timestamp = timestamp,
    )

    fun createTaxiLocationEntity(
        taxiId: String = "taxiID",
        timestamp: Instant = Instant.parse("2023-05-20T10:15:30Z"),
    ) = TaxiLocationEntity(
        taxiId = taxiId,
        latitude = BigDecimal(3.0),
        longitude = BigDecimal(4.0),
        timestamp = timestamp,
    )

    fun createTaxiLocationDto(taxiId: String = "taxiId") =
        TaxiLocationDto(
            taxiId = taxiId,
            latitude = "11.000234",
            longitude = "15.23456",
            timestamp = "2023-06-20T10:15:30Z",
        )
}
