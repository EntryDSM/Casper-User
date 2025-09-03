package hs.kr.entrydsm.user.infrastructure.kafka.producer

import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class DeleteAllTableProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, Unit>,
) : DeleteAllTableProducer {

    override fun send() {
        kafkaTemplate.send(KafkaTopics.DELETE_ALL_TABLE, Unit)
    }
}
