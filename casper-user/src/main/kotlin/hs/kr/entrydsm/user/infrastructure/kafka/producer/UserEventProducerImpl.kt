package hs.kr.entrydsm.user.infrastructure.kafka.producer

import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto.UserReceiptCodeUpdateCompletedEvent
import hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto.UserReceiptCodeUpdateFailedEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.UUID

/**
 * 사용자 이벤트를 발행하는 Producer 구현체입니다.
 * 
 * 접수번호 업데이트 성공/실패 결과를 다른 마이크로서비스에 알려
 * Choreography 패턴 기반의 분산 트랜잭션을 처리할 수 있도록 합니다.
 * 
 * @property userEventKafkaTemplate 사용자 이벤트 발행용 KafkaTemplate
 */
@Service
class UserEventProducerImpl(
    private val userEventKafkaTemplate: KafkaTemplate<String, Any>,
): UserEventProducer {

    /**
     * 접수번호 업데이트 완료 이벤트를 발행합니다.
     * 
     * 원서 서비스에서 요청한 접수번호 업데이트가 성공적으로 완료되었음을
     * 알리는 이벤트를 발행합니다.
     * 
     * @param receiptCode 업데이트된 접수번호
     * @param userId 사용자 ID
     */
    override fun sendReceiptCodeUpdateCompleted(receiptCode: Long, userId: UUID) {
        val event = UserReceiptCodeUpdateCompletedEvent(receiptCode, userId)
        userEventKafkaTemplate.send(KafkaTopics.USER_RECEIPT_CODE_UPDATE_COMPLETED, event)
    }

    /**
     * 접수번호 업데이트 실패 이벤트를 발행합니다.
     * 
     * 원서 서비스에서 요청한 접수번호 업데이트가 실패했음을 알려
     * 원서 서비스에서 보상 트랜잭션을 수행하도록 합니다.
     * 
     * @param receiptCode 업데이트 실패한 접수번호
     * @param userId 사용자 ID
     * @param reason 실패 사유
     */
    override fun sendReceiptCodeUpdateFailed(receiptCode: Long, userId: UUID, reason: String) {
        val event = UserReceiptCodeUpdateFailedEvent(receiptCode, userId, reason)
        userEventKafkaTemplate.send(KafkaTopics.USER_RECEIPT_CODE_UPDATE_FAILED, event)
    }

}
