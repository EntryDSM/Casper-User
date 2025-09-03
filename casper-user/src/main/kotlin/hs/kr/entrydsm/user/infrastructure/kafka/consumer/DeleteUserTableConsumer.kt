package hs.kr.entrydsm.user.infrastructure.kafka.consumer

import hs.kr.entrydsm.user.domain.user.application.port.out.DeleteUserPort
import hs.kr.entrydsm.user.infrastructure.kafka.configuration.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.transaction.annotation.Transactional

/**
 * 전체 테이블 삭제 이벤트를 수신하여 사용자 테이블을 삭제하는 Consumer 클래스입니다.
 * 
 * 관리자가 전체 데이터 초기화를 요청했을 때 사용자 테이블의 모든 데이터를
 * 삭제하는 역할을 담당합니다.
 * 
 * @property deleteUserPort 사용자 삭제 작업을 수행하는 포트
 */
open class DeleteUserTableConsumer(
    private val deleteUserPort: DeleteUserPort
) {
    
    /**
     * 전체 테이블 삭제 이벤트를 수신하여 모든 사용자 데이터를 삭제합니다.
     * 
     * DELETE_ALL_TABLE 토픽에서 이벤트를 수신하고,
     * 트랜잭션 내에서 모든 사용자 데이터를 삭제합니다.
     */
    @KafkaListener(
        topics = [KafkaTopics.DELETE_ALL_TABLE],
        groupId = "delete-all-table-user",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    open fun execute() = deleteUserPort.deleteAll()

}
