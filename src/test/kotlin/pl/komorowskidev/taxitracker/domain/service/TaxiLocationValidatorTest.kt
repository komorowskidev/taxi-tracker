package pl.komorowskidev.taxitracker.domain.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import pl.komorowskidev.taxitracker.common.exceptions.InvalidDateRangeException
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import java.time.Instant

class TaxiLocationValidatorTest {
    private val validator = TaxiLocationValidator()

    @Test
    fun `validate should not throw exception when taxi location is valid`() {
        // given
        val taxiId = "abc"
        val startTime = Instant.now()
        val endTime = startTime.plusMillis(1)
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, endTime)

        // when
        assertDoesNotThrow {
            validator.validate(taxiLocationRequest)
        }
    }

    @Test
    fun `validate should throw InvalidDateRangeException when start time is after end time`() {
        // given
        val taxiId = "abc"
        val startTime = Instant.now()
        val endTime = startTime.minusMillis(1)
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, endTime)

        // when
        assertThrows<InvalidDateRangeException> {
            validator.validate(taxiLocationRequest)
        }
    }
}
