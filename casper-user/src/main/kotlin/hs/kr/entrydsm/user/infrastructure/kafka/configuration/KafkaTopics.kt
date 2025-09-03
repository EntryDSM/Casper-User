package hs.kr.entrydsm.user.infrastructure.kafka.configuration

object KafkaTopics {
    const val DELETE_USER = "delete-user"
    const val DELETE_ALL_TABLE = "delete-all-table"
    const val CREATE_APPLICATION = "create-application"

    // Choreography 이벤트들
    const val USER_RECEIPT_CODE_UPDATE_COMPLETED = "user-receipt-code-update-completed"
    const val USER_RECEIPT_CODE_UPDATE_FAILED = "user-receipt-code-update-failed"
}