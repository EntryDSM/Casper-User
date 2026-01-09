package hs.kr.entrydsm.user.infrastructure.outbox.adapter

import hs.kr.entrydsm.user.domain.outbox.application.port.out.SaveOutboxEventPort
import hs.kr.entrydsm.user.domain.outbox.model.OutboxEvent
import hs.kr.entrydsm.user.infrastructure.outbox.entity.OutboxEventEntity
import hs.kr.entrydsm.user.infrastructure.outbox.repository.OutboxEventRepository
import org.springframework.stereotype.Component

/**
 * Outbox 이벤트 영속성 어댑터
 *
 * Domain 계층의 SaveOutboxEventPort를 구현합니다.
 *
 * @property outboxEventRepository Outbox 이벤트 JPA Repository
 */
@Component
class OutboxEventPersistenceAdapter(
    private val outboxEventRepository: OutboxEventRepository,
) : SaveOutboxEventPort {
    /**
     * Outbox 이벤트를 저장합니다.
     *
     * @param outboxEvent 저장할 Outbox 이벤트
     * @return 저장된 Outbox 이벤트 (ID 포함)
     */
    override fun save(outboxEvent: OutboxEvent): OutboxEvent {
        val entity =
            OutboxEventEntity(
                id = outboxEvent.id,
                aggregateType = outboxEvent.aggregateType,
                aggregateId = outboxEvent.aggregateId,
                eventType = outboxEvent.eventType,
                payload = outboxEvent.payload,
                createdAt = outboxEvent.createdAt,
            )

        val savedEntity = outboxEventRepository.save(entity)

        return OutboxEvent(
            id = savedEntity.id,
            aggregateType = savedEntity.aggregateType,
            aggregateId = savedEntity.aggregateId,
            eventType = savedEntity.eventType,
            payload = savedEntity.payload,
            createdAt = savedEntity.createdAt,
        )
    }
}
