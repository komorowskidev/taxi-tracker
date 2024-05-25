package pl.komorowskidev.taxitracker.domain.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import pl.komorowskidev.taxitracker.domain.ports.TaxiLocationPort
import java.math.BigDecimal
import java.time.Instant

class TaxiLocationServiceTest {
    private val taxiLocationPortMock: TaxiLocationPort = mock()
    private val taxiLocationService = TaxiLocationService(taxiLocationPortMock)

    @Test
    fun `saveTaxiLocation should call save method`() {
        // given
        val taxiLocation = TaxiLocation("abc", BigDecimal("1.11223344"), BigDecimal("2.55667788"), Instant.now())

        // when
        taxiLocationService.saveTaxiLocation(taxiLocation)

        // then
        verify(taxiLocationPortMock).save(taxiLocation)
    }
}
