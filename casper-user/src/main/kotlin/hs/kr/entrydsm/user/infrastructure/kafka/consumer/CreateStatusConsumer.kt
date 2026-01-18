package hs.kr.entrydsm.user.infrastructure.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.entrydsm.user.domain.user.application.port.`in`.DeleteReceiptCodeUseCase
import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CreateStatusConsumer(
    private val mapper: ObjectMapper,
    private val deleteReceiptCodeUseCase: DeleteReceiptCodeUseCase
) {

    @KafkaListener(
        topics = [KafkaTopics.APPLICATION_STATUS_CREATE_FAILED],
        groupId = "delete-receipt-code",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun applicationStatusCreateFailed(message: String) {
        val receiptCode = mapper.readValue(message, Long::class.java)
        deleteReceiptCodeUseCase.deleteReceiptCode(receiptCode)
    }
}