package hs.kr.entrydsm.user.domain.user.facade

import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserFacadeUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import hs.kr.entrydsm.user.domain.user.model.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * 사용자 관련 공통 기능을 제공하는 파사드 클래스입니다.
 * 여러 계층에서 공통으로 사용되는 사용자 조회 기능을 중앙화합니다.
 *
 * @property queryUserPort 사용자 조회 포트
 */
@Component
class UserFacade(
    private val queryUserPort: QueryUserPort,
) : UserFacadeUseCase {
    /**
     * 현재 인증된 사용자를 조회합니다.
     * Spring Security 컨텍스트에서 사용자 ID를 추출하여 사용자 정보를 반환합니다.
     *
     * @return 현재 인증된 사용자
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     */
    override fun getCurrentUser(): User {
        val userId = SecurityContextHolder.getContext().authentication.name
        return queryUserPort.findById(UUID.fromString(userId)) ?: throw UserNotFoundException
    }

    /**
     * 전화번호로 사용자를 조회합니다.
     *
     * @param phoneNumber 조회할 전화번호
     * @return 조회된 사용자
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     */
    override fun getUserByPhoneNumber(phoneNumber: String): User {
        return queryUserPort.findByPhoneNumber(phoneNumber) ?: throw UserNotFoundException
    }
}
