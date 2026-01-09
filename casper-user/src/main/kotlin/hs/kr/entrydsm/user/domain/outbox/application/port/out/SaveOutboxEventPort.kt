package hs.kr.entrydsm.user.domain.outbox.application.port.out

import hs.kr.entrydsm.user.domain.outbox.model.OutboxEvent

/**
 * Outbox 이벤트 저장 Port 인터페이스
 *
 * Domain 계층에서 Infrastructure 계층의 Outbox 저장소에 접근하기 위한 인터페이스입니다.
 */
interface SaveOutboxEventPort {
    /**
     * Outbox 이벤트를 저장합니다.
     *
     * 도메인 엔티티와 동일 트랜잭션으로 저장되어 원자성을 보장합니다.
     *
     * @param outboxEvent 저장할 Outbox 이벤트
     * @return 저장된 Outbox 이벤트 (ID 포함)
     */
    fun save(outboxEvent: OutboxEvent): OutboxEvent
}
