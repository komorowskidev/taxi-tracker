package pl.komorowskidev.taxitracker.interfaces.sqs.taxievent

import io.awspring.cloud.sqs.annotation.SqsListener
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.common.LoggerDelegate
import pl.komorowskidev.taxitracker.domain.service.TaxiLocationService

@Component
class TaxiEventListener(
    private val taxiLocationService: TaxiLocationService,
    private val taxiEventMapper: TaxiEventMapper,
) {
    private val logger by LoggerDelegate()

    @SqsListener("\${events.queues.taxi.event.sqs.url}")
    fun receiveMessage(message: Message<TaxiEventDto>) {
        logger.info("Received message: ${message.payload}")
        val taxiEventDto = message.payload
        try {
            taxiLocationService.saveTaxiLocation(taxiEventMapper.toDomain(taxiEventDto))
            Acknowledgement.acknowledge(message)
        } catch (ex: Exception) {
            logger.error(ex.message)
        }
    }
}
