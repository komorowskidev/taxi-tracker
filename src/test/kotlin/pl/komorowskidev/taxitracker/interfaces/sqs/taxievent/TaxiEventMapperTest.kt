package pl.komorowskidev.taxitracker.interfaces.sqs.taxievent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.komorowskidev.taxitracker.testutils.TestDataFactory

class TaxiEventMapperTest {
    private val mapper = TaxiEventMapper()
    private val testDataFactory = TestDataFactory()

    @Test
    fun `toDomain should map Dto to domain`() {
        // given
        val dto = testDataFactory.createTaxiEventDto()

        // when
        val actual = mapper.toDomain(dto)

        // then
        assertEquals(dto.taxiId, actual.taxiId)
        assertEquals(dto.latitude, actual.latitude)
        assertEquals(dto.longitude, actual.longitude)
        assertEquals(dto.timestamp, actual.timestamp)
    }
}
