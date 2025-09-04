package hs.kr.entrydsm.user.infrastructure.kafka.configuration

/**
 * Kafka 토픽명을 관리하는 상수 객체입니다.
 * 
 * 마이크로서비스 간 이벤트 통신에 사용되는 토픽명들을 중앙에서 관리하여
 * 토픽명 변경 시 일관성을 보장합니다.
 */
object KafkaTopics {
    /**
     * 사용자 삭제 이벤트 토픽
     * 사용자가 탈퇴했을 때 다른 서비스에 알리기 위해 사용
     */
    const val DELETE_USER = "delete-user"
    
    /**
     * 전체 테이블 삭제 이벤트 토픽
     * 관리자가 전체 데이터를 초기화할 때 다른 서비스에 알리기 위해 사용
     */
    const val DELETE_ALL_TABLE = "delete-all-table"
    
    /**
     * 원서 생성 이벤트 토픽
     * 원서 서비스에서 원서가 생성되었을 때 사용자 접수번호 업데이트를 위해 사용
     */
    const val CREATE_APPLICATION = "create-application"

    // Choreography 이벤트들
    
    /**
     * 사용자 접수번호 업데이트 완료 이벤트 토픽
     * 사용자 서비스에서 접수번호 업데이트가 성공적으로 완료되었음을 알리는 이벤트
     */
    const val USER_RECEIPT_CODE_UPDATE_COMPLETED = "user-receipt-code-update-completed"
    
    /**
     * 사용자 접수번호 업데이트 실패 이벤트 토픽
     * 사용자 서비스에서 접수번호 업데이트가 실패했음을 알리는 이벤트
     * 원서 서비스에서 이 이벤트를 수신하면 보상 트랜잭션을 수행함
     */
    const val USER_RECEIPT_CODE_UPDATE_FAILED = "user-receipt-code-update-failed"
}
