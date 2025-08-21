package hs.kr.entrydsm.user.infrastructure.kafka.producer

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

/**
 * Kafka가 도입되기 전까지 사용할 임시 Mock Producer입니다.
 */
@Component
@Profile("!kafka")
class MockDeleteAllTableProducer : DeleteAllTableProducer {
    override fun send() {
        // TODO: Kafka 도입 후 실제 구현체로 교체
        println("Mock: DeleteAllTable event sent (Kafka not available)")
    }
}