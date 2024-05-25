package pl.komorowskidev.taxitracker.domain.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import pl.komorowskidev.taxitracker.domain.model.Taxi
import pl.komorowskidev.taxitracker.domain.ports.TaxiPort
import java.math.BigDecimal
import java.time.Instant

class TaxiServiceTest {
    private val taxiPortMock: TaxiPort = mock()
    private val taxiService = TaxiService(taxiPortMock)

    @Test
    fun `saveTaxiLocation should call save method`() {
        // given
        val taxi = Taxi("abc", BigDecimal("1.11223344"), BigDecimal("2.55667788"), Instant.now())

        // when
        taxiService.saveTaxiLocation(taxi)

        // then
        verify(taxiPortMock).save(taxi)
    }
}
