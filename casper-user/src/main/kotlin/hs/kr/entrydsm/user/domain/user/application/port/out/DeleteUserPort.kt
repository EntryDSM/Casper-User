package hs.kr.entrydsm.user.domain.user.application.port.out

import hs.kr.entrydsm.user.domain.user.model.User
import java.util.UUID

/**
 * 사용자 삭제 작업을 위한 포트 인터페이스입니다.
 * 헥사고날 아키텍처에서 도메인 계층이 인프라스트럭처 계층과 통신하기 위한 인터페이스입니다.
 */
interface DeleteUserPort {
    /**
     * ID로 사용자를 삭제합니다.
     *
     * @param userId 삭제할 사용자 ID
     */
    fun deleteById(userId: UUID)

    /**
     * 지정된 일수보다 오래된 탈퇴 사용자를 조회합니다.
     *
     * @param days 기준 일수
     * @return 삭제 대상 사용자 목록
     */
    fun findWithdrawnUsersOlderThan(days: Long): List<User>
}
