package pl.komorowskidev.taxitracker.domain.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import java.time.Instant
import kotlin.random.Random

class TaxiLocationValidatorTest {
    private val validator = TaxiLocationValidator()

    @Test
    fun `validate should return Valid when startTime is before endTime`() {
        // given
        val taxiId = RandomStringUtils.randomAlphanumeric(20)
        val startTime = Instant.now()
        val endTime = startTime.plusMillis(Random.nextLong(100))
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, endTime)

        // when
        val actual = validator.validate(taxiLocationRequest)

        // then
        assertThat(actual).isInstanceOf(Validation.Valid::class.java)
    }

    @Test
    fun `validate should return Valid when startTime is equal endTime`() {
        // given
        val taxiId = RandomStringUtils.randomAlphanumeric(20)
        val startTime = Instant.now()
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, startTime)

        // when
        val actual = validator.validate(taxiLocationRequest)

        // then
        assertThat(actual).isInstanceOf(Validation.Valid::class.java)
    }

    @Test
    fun `validate should return Invalid when start time is after end time`() {
        // given
        val taxiId = RandomStringUtils.randomAlphanumeric(20)
        val startTime = Instant.now()
        val endTime = startTime.minusMillis(Random.nextLong(100))
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, endTime)

        // when
        val actual = validator.validate(taxiLocationRequest)

        // then
        assertThat(actual).isInstanceOf(Validation.Invalid::class.java)
    }
}
