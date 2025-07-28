package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.response.UserResponse
import hs.kr.entrydsm.user.domain.user.application.port.`in`.QueryUserInfoUseCase
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 정보 조회 서비스 클래스입니다.
 * 현재 인증된 사용자의 개인정보를 조회하여 응답 형태로 변환하는 처리를 담당합니다.
 *
 * @property userFacadeUseCase 사용자 파사드 유스케이스
 */
@Service
@Transactional(readOnly = true)
class QueryUserInfoService(
    private val userFacadeUseCase: UserFacadeUseCase,
) : QueryUserInfoUseCase {
    /**
     * 현재 로그인한 사용자의 정보를 조회합니다.
     * Spring Security 컨텍스트에서 인증된 사용자 정보를 가져와 응답 형태로 변환합니다.
     *
     * @return 사용자 정보 응답
     */
    override fun getUserInfo(): UserResponse {
        val user = userFacadeUseCase.getCurrentUser()

        return user.run {
            UserResponse(
                name = name,
                phoneNumber = phoneNumber,
                isParent = isParent,
            )
        }
    }
}
