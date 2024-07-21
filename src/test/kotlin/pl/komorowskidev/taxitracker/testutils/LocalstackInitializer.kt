package pl.komorowskidev.taxitracker.testutils

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

@Testcontainers
class LocalstackInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        private val container = LocalStackContainer(DockerImageName.parse(DockerImageNameMap.getFullImageName("localstack")))
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val taxiEventSqsName = "taxi-event-queue"
        container.start()
        container.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", taxiEventSqsName)
        applicationContext.onClose { container.stop() }

        // set properties
        TestPropertyValues
            .of(
                "spring.cloud.aws.region.static:${container.region}",
                "spring.cloud.aws.credentials.access-key-id:${container.accessKey}",
                "spring.cloud.aws.credentials.secret-access-key:${container.secretKey}",
                "spring.cloud.aws.sqs.endpoint:${container.getEndpointOverride(LocalStackContainer.Service.SQS)}",
                "spring.cloud.aws.region.static:${container.region}",
                "events.queues.taxi.event.sqs.url:$taxiEventSqsName",
            ).applyTo(applicationContext.environment)

        val sqsClient =
            SqsClient
                .builder()
                .region(
                    Region.of(container.region),
                ).endpointOverride(container.getEndpointOverride(LocalStackContainer.Service.SQS))
                .build()
        val beanFactory = applicationContext.beanFactory
        beanFactory.registerSingleton("sqsClient", sqsClient)
    }
}
