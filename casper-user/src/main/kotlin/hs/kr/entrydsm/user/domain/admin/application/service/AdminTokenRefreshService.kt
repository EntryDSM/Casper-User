package hs.kr.entrydsm.user.domain.admin.application.service

import hs.kr.entrydsm.user.domain.admin.application.port.`in`.AdminTokenRefreshUseCase
import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserInfo
import hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository.UserInfoRepository
import hs.kr.entrydsm.user.global.security.jwt.JwtProperties
import hs.kr.entrydsm.user.global.security.jwt.JwtTokenProvider
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 관리자 토큰 갱신 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
class AdminTokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userInfoRepository: UserInfoRepository,
    private val jwtProperties: JwtProperties,
) : AdminTokenRefreshUseCase {
    /**
     * 관리자의 리프레시 토큰을 이용하여 새로운 액세스 토큰을 발급합니다.
     */
    @Transactional
    override fun refresh(refreshToken: String): TokenResponse {
        val tokenResponse = jwtTokenProvider.reIssue(refreshToken)

        val userInfo =
            UserInfo(
                token = tokenResponse.accessToken,
                userId = jwtTokenProvider.getSubjectWithExpiredCheck(tokenResponse.accessToken),
                userRole = jwtTokenProvider.getRole(tokenResponse.accessToken),
                ttl = jwtProperties.accessExp,
            )
        userInfoRepository.save(userInfo)

        return tokenResponse
    }
}
