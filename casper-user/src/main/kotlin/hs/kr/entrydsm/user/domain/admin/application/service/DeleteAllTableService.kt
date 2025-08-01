package hs.kr.entrydsm.user.domain.admin.application.service

import hs.kr.entrydsm.user.domain.admin.application.port.`in`.DeleteAllTableUseCase
import hs.kr.entrydsm.user.infrastructure.kafka.producer.DeleteAllTableProducer
import org.springframework.stereotype.Service

/**
 * 모든 테이블 삭제 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
class DeleteAllTableService(
    private val deleteAllTableProducer: DeleteAllTableProducer,
) : DeleteAllTableUseCase {
    /**
     * 모든 테이블을 삭제합니다.
     */
    override fun deleteAllTables() = deleteAllTableProducer.send()
}
