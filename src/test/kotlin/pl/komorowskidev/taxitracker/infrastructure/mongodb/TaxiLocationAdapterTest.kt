package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import java.math.BigDecimal
import java.time.Instant

class TaxiLocationAdapterTest {
    private val taxiLocationRepositoryMock: TaxiLocationRepository = mock()
    private val taxiLocationMapperMock: TaxiLocationMapper = mock()

    private val taxiAdapter = TaxiLocationAdapter(taxiLocationRepositoryMock, taxiLocationMapperMock)

    @Test
    fun `should call repository`() {
        // Given
        val taxiLocation = TaxiLocation("123", BigDecimal("456"), BigDecimal("789"), Instant.now())
        val taxiLocationEntity = TaxiLocationEntity("123", BigDecimal("456"), BigDecimal("789"), Instant.now())
        whenever(taxiLocationMapperMock.toEntity(taxiLocation)).thenReturn(taxiLocationEntity)

        // When
        taxiAdapter.save(taxiLocation)

        // Then
        verify(taxiLocationRepositoryMock).save(taxiLocationEntity)
    }
}
