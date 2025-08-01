package hs.kr.entrydsm.user.domain.user.application.port.out

import hs.kr.entrydsm.user.domain.user.model.User
import java.util.UUID

/**
 * 사용자 조회 작업을 위한 포트 인터페이스입니다.
 * 헥사고날 아키텍처에서 도메인 계층이 인프라스트럭처 계층과 통신하기 위한 인터페이스입니다.
 */
interface QueryUserPort {
    /**
     * ID로 사용자를 조회합니다.
     *
     * @param userId 조회할 사용자 ID
     * @return 조회된 사용자 도메인 모델 (없으면 null)
     */
    fun findById(userId: UUID): User?

    /**
     * 전화번호로 사용자를 조회합니다.
     *
     * @param phoneNumber 조회할 전화번호
     * @return 조회된 사용자 도메인 모델 (없으면 null)
     */
    fun findByPhoneNumber(phoneNumber: String): User?

    /**
     * 전화번호로 사용자 존재 여부를 확인합니다.
     *
     * @param phoneNumber 확인할 전화번호
     * @return 사용자 존재 여부
     */
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
