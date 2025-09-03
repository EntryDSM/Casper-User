package hs.kr.entrydsm.user.infrastructure.kafka.consumer

import hs.kr.entrydsm.user.domain.user.application.port.out.DeleteUserPort
import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.transaction.annotation.Transactional

open class DeleteUserTableConsumer(
    private val deleteUserPort: DeleteUserPort
) {
    @KafkaListener(
        topics = [KafkaTopics.DELETE_ALL_TABLE],
        groupId = "delete-all-table-user",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    open fun execute() = deleteUserPort.deleteAll()

}