package hs.kr.entrydsm.user.infrastructure.kafka.producer

import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

/**
 * 사용자 삭제 이벤트를 발행하는 Producer 구현체입니다.
 * 
 * 사용자 탈퇴 시 해당 사용자의 접수번호를 다른 마이크로서비스에 알려
 * 연관된 데이터를 함께 삭제할 수 있도록 합니다.
 * 
 * @property kafkaTemplate 사용자 삭제 이벤트 발행용 KafkaTemplate
 */
@Component
class DeleteUserProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, Long>
) : DeleteUserProducer {
    
    /**
     * 사용자 삭제 이벤트를 Kafka로 발행합니다.
     * 
     * DELETE_USER 토픽에 접수번호를 전송하여 다른 서비스에서
     * 해당 접수번호와 연관된 데이터를 삭제하도록 합니다.
     * 
     * @param receiptCode 삭제된 사용자의 접수번호
     */
    override fun send(receiptCode: Long) {
        kafkaTemplate.send(KafkaTopics.DELETE_USER, receiptCode)
    }
}
