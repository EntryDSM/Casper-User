package hs.kr.entrydsm.user.infrastructure.kafka.producer

import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

/**
 * 전체 테이블 삭제 이벤트를 발행하는 Producer 구현체입니다.
 *
 * 관리자가 전체 데이터를 초기화할 때 다른 마이크로서비스에 알려
 * 모든 관련 데이터를 함께 삭제할 수 있도록 합니다.
 *
 * @property kafkaTemplate 전체 테이블 삭제 이벤트 발행용 KafkaTemplate
 */
@Component
class DeleteAllTableProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, Unit>,
) : DeleteAllTableProducer {
    /**
     * 전체 테이블 삭제 이벤트를 Kafka로 발행합니다.
     *
     * DELETE_ALL_TABLE 토픽에 Unit 값을 전송하여 다른 서비스에서
     * 전체 데이터 삭제를 수행하도록 합니다.
     */
    override fun send() {
        kafkaTemplate.send(KafkaTopics.DELETE_ALL_TABLE, Unit)
    }
}
