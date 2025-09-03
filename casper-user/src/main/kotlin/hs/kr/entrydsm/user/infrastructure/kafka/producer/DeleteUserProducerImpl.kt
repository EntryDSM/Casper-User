package hs.kr.entrydsm.user.infrastructure.kafka.producer

import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class DeleteUserProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, Long>
) : DeleteUserProducer {
    override fun send(receiptCode: Long) {
        kafkaTemplate.send(KafkaTopics.DELETE_USER, receiptCode)
    }
}