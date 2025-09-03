package hs.kr.entrydsm.user.infrastructure.kafka.consumer.dto

import java.util.UUID

data class CreateApplicationEvent(
    val receiptCode: Long,
    val userId: UUID
)