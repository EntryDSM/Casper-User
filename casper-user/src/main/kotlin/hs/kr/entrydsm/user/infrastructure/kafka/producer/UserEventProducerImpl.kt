package hs.kr.entrydsm.user.infrastructure.kafka.producer

import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

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
) : UserEventProducer {

    override fun sendReceiptCodeUpdateCompleted(receiptCode: Long) {
        userEventKafkaTemplate.send(KafkaTopics.USER_RECEIPT_CODE_UPDATE_COMPLETED, receiptCode)
    }

    override fun sendReceiptCodeUpdateFailed(receiptCode: Long) {
        userEventKafkaTemplate.send(KafkaTopics.USER_RECEIPT_CODE_UPDATE_FAILED, receiptCode)
    }
}
