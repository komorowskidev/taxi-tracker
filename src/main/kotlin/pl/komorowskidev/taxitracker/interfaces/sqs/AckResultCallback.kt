package pl.komorowskidev.taxitracker.interfaces.sqs

import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementResultCallback
import org.springframework.messaging.Message
import pl.komorowskidev.taxitracker.common.LoggerDelegate

class AckResultCallback : AcknowledgementResultCallback<Any?> {
    private val logger by LoggerDelegate()

    override fun onSuccess(messages: MutableCollection<Message<Any?>>) {
        logger.info("AckResultCallback.onSuccess")
    }

    override fun onFailure(
        messages: MutableCollection<Message<Any?>>,
        t: Throwable,
    ) {
        logger.error("AckResultCallback.onFailure", t)
    }
}
