package hs.kr.entrydsm.user.infrastructure.kafka.producer

import java.util.UUID

/**
 * 사용자 이벤트를 발행하는 Producer 인터페이스입니다.
 *
 * 접수번호 업데이트 성공/실패 이벤트를 발행하여
 * Choreography 패턴 기반의 분산 트랜잭션을 지원합니다.
 */
interface UserEventProducer {
    /**
     * 접수번호 업데이트 완료 이벤트를 발행합니다.
     *
     * @param receiptCode 업데이트된 접수번호
     * @param userId 사용자 ID
     */
    fun sendReceiptCodeUpdateCompleted(
        receiptCode: Long,
        userId: UUID,
    )

    /**
     * 접수번호 업데이트 실패 이벤트를 발행합니다.
     *
     * @param receiptCode 업데이트 실패한 접수번호
     * @param userId 사용자 ID
     * @param reason 실패 사유
     */
    fun sendReceiptCodeUpdateFailed(
        receiptCode: Long,
        userId: UUID,
        reason: String,
    )
}
