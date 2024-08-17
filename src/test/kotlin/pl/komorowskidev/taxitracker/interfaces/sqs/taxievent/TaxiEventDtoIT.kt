package pl.komorowskidev.taxitracker.interfaces.sqs.taxievent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.testcontainers.shaded.org.awaitility.Awaitility.await
import pl.komorowskidev.taxitracker.infrastructure.mongodb.TaxiLocationRepository
import pl.komorowskidev.taxitracker.testutils.SqsUtil
import pl.komorowskidev.taxitracker.testutils.TaxiTrackerSpringBootTest
import pl.komorowskidev.taxitracker.testutils.TestDataFactory
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import java.util.concurrent.TimeUnit

@TaxiTrackerSpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxiEventDtoIT {
    @Value("\${events.queues.taxi.event.sqs.url}")
    private lateinit var url: String

    @Autowired
    private lateinit var sqsClient: SqsClient

    @Autowired
    private lateinit var taxiLocationRepository: TaxiLocationRepository

    @Autowired
    private lateinit var sqsUtil: SqsUtil

    private val testDataFactory = TestDataFactory()

    @BeforeEach
    fun setup() {
        taxiLocationRepository.deleteAll()
    }

    @Test
    fun `should read message from SQS queue and create taxiLocation in DB`() {
        // given
        val taxiEventDto = testDataFactory.createTaxiEventDto()
        val payload = testDataFactory.createSqsPayload(taxiEventDto)
        val sendMessageRequest =
            SendMessageRequest
                .builder()
                .queueUrl(url)
                .messageBody(payload)
                .build()

        // when
        sqsClient.sendMessage(sendMessageRequest)

        // then
        await().atMost(10, TimeUnit.SECONDS).until {
            sqsUtil.approximateNumberOfMessagesIncludingDelayedAndNotVisible(sqsClient, url) == 0
        }

        val taxiLocations = taxiLocationRepository.findAll()
        assertEquals(1, taxiLocations.size)
        assertEquals(taxiEventDto.taxiId, taxiLocations[0].taxiId)
        assertEquals(taxiEventDto.latitude, taxiLocations[0].latitude)
        assertEquals(taxiEventDto.longitude, taxiLocations[0].longitude)
        assertEquals(taxiEventDto.timestamp, taxiLocations[0].timestamp)
    }
}
