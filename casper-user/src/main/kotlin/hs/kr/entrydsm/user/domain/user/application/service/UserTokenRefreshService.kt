package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserInfo
import hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository.UserInfoRepository
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserTokenRefreshUseCase
import hs.kr.entrydsm.user.global.security.jwt.JwtProperties
import hs.kr.entrydsm.user.global.security.jwt.JwtTokenProvider
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 토큰 갱신 서비스 클래스입니다.
 * 만료된 액세스 토큰을 리프레시 토큰으로 갱신하는 처리를 담당합니다.
 *
 * @property jwtTokenProvider JWT 토큰 제공자
 * @property jwtProperties JWT 설정 프로퍼티
 * @property userInfoRepository 사용자 정보 저장소
 */
@Transactional
@Service
class UserTokenRefreshService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtProperties: JwtProperties,
    private val userInfoRepository: UserInfoRepository,
) : UserTokenRefreshUseCase {
    /**
     * 리프레시 토큰을 이용하여 새로운 액세스 토큰을 발급합니다.
     * 새로운 토큰 정보를 Redis에 저장하여 인증 상태를 유지합니다.
     *
     * @param refreshToken 기존 리프레시 토큰
     * @return 새로 발급된 토큰 응답
     */
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
