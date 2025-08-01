package hs.kr.entrydsm.user.domain.admin.service

import hs.kr.entrydsm.user.infrastructure.kafka.producer.DeleteAllTableProducer
import org.springframework.stereotype.Service

@Service
class DeleteAllTableService(
    private val deleteAllTableProducer: DeleteAllTableProducer,
) {
    fun execute() = deleteAllTableProducer.send()
}
