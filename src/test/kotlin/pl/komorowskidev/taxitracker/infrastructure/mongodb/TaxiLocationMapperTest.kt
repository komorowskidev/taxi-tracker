package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import java.math.BigDecimal
import java.time.Instant

class TaxiLocationMapperTest {
    private val mapper = TaxiLocationMapper()

    @Test
    fun `should map Taxi to TaxiEntity correctly`() {
        // Given
        val taxiLocation =
            TaxiLocation(
                id = "taxi123",
                latitude = BigDecimal("52.237049"),
                longitude = BigDecimal("21.017532"),
                timestamp = Instant.parse("2023-04-20T10:15:30Z"),
            )

        // When
        val entity = mapper.toEntity(taxiLocation)

        // Then
        assertEquals(taxiLocation.id, entity.id)
        assertEquals(taxiLocation.latitude, entity.latitude)
        assertEquals(taxiLocation.longitude, entity.longitude)
        assertEquals(taxiLocation.timestamp, entity.timestamp)
    }
}
