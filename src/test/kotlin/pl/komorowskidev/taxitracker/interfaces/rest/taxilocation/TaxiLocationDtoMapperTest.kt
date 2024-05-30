package pl.komorowskidev.taxitracker.interfaces.rest.taxilocation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.komorowskidev.taxitracker.testutils.TestDataFactory
import java.math.BigDecimal
import java.time.Instant

class TaxiLocationDtoMapperTest {
    private val taxiLocationDtoMapper = TaxiLocationDtoMapper()

    private val testDataFactory = TestDataFactory()

    @Test
    fun `toDto should map taxiLocation to DTO`() {
        // Given
        val latitude = "1.000023"
        val longitude = "2.00002345"
        val timestamp = "2023-04-20T10:15:30Z"
        val taxiLocation =
            testDataFactory.createTaxiLocation(
                latitude = BigDecimal(latitude),
                longitude = BigDecimal(longitude),
                timestamp = Instant.parse(timestamp),
            )

        // When
        val actual = taxiLocationDtoMapper.toDto(taxiLocation)

        // Then
        assertEquals(taxiLocation.taxiId, actual.taxiId)
        assertEquals(latitude, actual.latitude)
        assertEquals(longitude, actual.longitude)
        assertEquals(timestamp, actual.timestamp)
    }
}
