package pl.komorowskidev.taxitracker.interfaces.sqs.taxievent

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.messaging.support.GenericMessage
import pl.komorowskidev.taxitracker.domain.service.TaxiLocationService
import pl.komorowskidev.taxitracker.testutils.CustomAcknowledgementCallback
import pl.komorowskidev.taxitracker.testutils.TestDataFactory

class TaxiEventListenerTest {
    private val taxiLocationServiceMock: TaxiLocationService = mock()
    private val taxiEventMapperMock: TaxiEventMapper = mock()
    private val listener = TaxiEventListener(taxiLocationServiceMock, taxiEventMapperMock)

    private val testDataFactory = TestDataFactory()

    @Test
    fun `should send mapped payload to the service`() {
        // given
        val taxiEventDto = testDataFactory.createTaxiEventDto()
        val taxiLocation = testDataFactory.createTaxiLocation()
        val message = GenericMessage(taxiEventDto, mapOf("AcknowledgementCallback" to CustomAcknowledgementCallback()))
        whenever(taxiEventMapperMock.toDomain(taxiEventDto)).thenReturn(taxiLocation)

        // when
        listener.receiveMessage(message)

        // then
        verify(taxiLocationServiceMock).saveTaxiLocation(taxiLocation)
    }
}
