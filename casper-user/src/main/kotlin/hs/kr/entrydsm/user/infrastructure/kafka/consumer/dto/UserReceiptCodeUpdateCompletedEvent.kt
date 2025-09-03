package hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto

import java.util.UUID

data class UserReceiptCodeUpdateCompletedEvent(
    val receiptCode: Long,
    val userId: UUID
)
