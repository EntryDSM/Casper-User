package hs.kr.entrydsm.user.infrastructure.kafka.producer

import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto.UserReceiptCodeUpdateCompletedEvent
import hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto.UserReceiptCodeUpdateFailedEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserEventProducerImpl(
    private val userEventKafkaTemplate: KafkaTemplate<String, Any>,
): UserEventProducer {

    override fun sendReceiptCodeUpdateCompleted(receiptCode: Long, userId: UUID) {
        val event = UserReceiptCodeUpdateCompletedEvent(receiptCode, userId)
        userEventKafkaTemplate.send(KafkaTopics.USER_RECEIPT_CODE_UPDATE_COMPLETED, event)
    }

    override fun sendReceiptCodeUpdateFailed(receiptCode: Long, userId: UUID, reason: String) {
        val event = UserReceiptCodeUpdateFailedEvent(receiptCode, userId, reason)
        userEventKafkaTemplate.send(KafkaTopics.USER_RECEIPT_CODE_UPDATE_FAILED, event)
    }

}