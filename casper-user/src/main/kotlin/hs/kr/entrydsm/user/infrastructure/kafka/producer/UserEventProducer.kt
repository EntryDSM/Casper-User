package hs.kr.entrydsm.user.infrastructure.kafka.producer

import java.util.UUID

interface UserEventProducer {
    fun sendReceiptCodeUpdateCompleted(receiptCode: Long, userId: UUID)
    fun sendReceiptCodeUpdateFailed(receiptCode: Long, userId: UUID, reason: String)
}