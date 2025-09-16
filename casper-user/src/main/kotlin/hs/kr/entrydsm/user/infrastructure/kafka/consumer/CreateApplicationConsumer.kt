package hs.kr.entrydsm.user.infrastructure.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.entrydsm.user.domain.user.application.port.`in`.ChangeReceiptCodeUseCase
import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto.CreateApplicationEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

/**
 * 지원서 생성 이벤트를 처리하는 Kafka 컨슈머 클래스입니다.
 * 사용자의 수험번호 변경 요청을 처리합니다.
 */
@Service
class CreateApplicationConsumer(
    private val changeReceiptCodeUseCase: ChangeReceiptCodeUseCase,
    private val mapper: ObjectMapper,
) {
    @KafkaListener(
        topics = [KafkaTopics.CREATE_APPLICATION],
        groupId = "change-user-receipt-code-consumer",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun execute(message: String) {
        val createApplicationEvent = mapper.readValue(message, CreateApplicationEvent::class.java)
        changeReceiptCodeUseCase.changeReceiptCode(createApplicationEvent.userId, createApplicationEvent.receiptCode)
    }
}
