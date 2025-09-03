package hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto

import java.util.UUID

data class UserReceiptCodeUpdateFailedEvent(
    val receiptCode: Long,
    val userId: UUID,
    val reason: String
)
