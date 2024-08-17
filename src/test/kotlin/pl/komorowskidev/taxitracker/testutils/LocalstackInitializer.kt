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
        private val container =
            LocalStackContainer(DockerImageName.parse(DockerImageNameMap.getFullImageName("localstack")))
                .withEnv("AWS_ACCESS_KEY_ID", System.getenv("AWS_ACCESS_KEY_ID") ?: "default_key")
                .withEnv("AWS_SECRET_ACCESS_KEY", System.getenv("AWS_SECRET_ACCESS_KEY") ?: "default_secret")
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val taxiEventSqsName = "taxi-event-queue"
        container.start()
        container.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", taxiEventSqsName)
        applicationContext.onClose { container.stop() }

        TestPropertyValues
            .of(
                "spring.cloud.aws.region.static:${container.region}",
                "spring.cloud.aws.sqs.endpoint:${container.getEndpointOverride(LocalStackContainer.Service.SQS)}",
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
