package pl.komorowskidev.taxitracker.testutils

import org.springframework.stereotype.Component
import org.testcontainers.shaded.com.google.common.collect.ImmutableList
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesRequest

@Component
class SqsUtil {
    fun approximateNumberOfMessagesIncludingDelayedAndNotVisible(
        sqsClient: SqsClient,
        queueUrl: String,
    ): Int {
        val attributeKeys =
            ImmutableList.of(
                "ApproximateNumberOfMessages",
                "ApproximateNumberOfMessagesDelayed",
                "ApproximateNumberOfMessagesNotVisible",
            )
        return sumOfAttributeValues(sqsClient, queueUrl, attributeKeys)
    }

    private fun sumOfAttributeValues(
        sqsClient: SqsClient,
        queueUrl: String,
        requestedAttributes: ImmutableList<String>,
    ): Int {
        val getQueueAttributesRequest =
            GetQueueAttributesRequest
                .builder()
                .queueUrl(
                    queueUrl,
                ).attributeNamesWithStrings(requestedAttributes)
                .build()
        val queueAttributes = sqsClient.getQueueAttributes(getQueueAttributesRequest)
        return requestedAttributes
            .stream()
            .mapToLong { attributeKey ->
                val attributeValue = queueAttributes.attributesAsStrings().getValue(attributeKey)
                when (val longValue = attributeValue.toLongOrNull()) {
                    null -> throw IllegalArgumentException("Attribute value is not a number: $attributeValue")
                    else -> longValue
                }
            }.sum()
            .toInt()
    }
}
