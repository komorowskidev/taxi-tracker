package pl.komorowskidev.taxitracker.interfaces.sqs

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import io.awspring.cloud.sqs.listener.SqsContainerOptionsBuilder
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.time.Duration

@Configuration
class SqsConfiguration {
    @Bean
    fun defaultSqsListenerContainerFactory(sqsAsyncClient: SqsAsyncClient): SqsMessageListenerContainerFactory<Any> =
        SqsMessageListenerContainerFactory
            .builder<Any>()
            .configure { options: SqsContainerOptionsBuilder ->
                options
                    .acknowledgementMode(AcknowledgementMode.MANUAL)
                    .acknowledgementInterval(
                        Duration.ofSeconds(3),
                    ).acknowledgementThreshold(0)
            }.acknowledgementResultCallback(AckResultCallback())
            .sqsAsyncClient(sqsAsyncClient)
            .build()
}
