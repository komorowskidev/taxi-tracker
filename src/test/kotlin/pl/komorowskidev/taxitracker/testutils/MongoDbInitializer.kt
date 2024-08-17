package pl.komorowskidev.taxitracker.testutils

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
class MongoDbInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        private val container = MongoDBContainer(DockerImageName.parse(DockerImageNameMap.getFullImageName("mongo")))
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        container.start()
        applicationContext.onClose { container.stop() }
        TestPropertyValues
            .of(
                "spring.data.mongodb.uri:${container.replicaSetUrl}",
            ).applyTo(applicationContext.environment)
    }
}
