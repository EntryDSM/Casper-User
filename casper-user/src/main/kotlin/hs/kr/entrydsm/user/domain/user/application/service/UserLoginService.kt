package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.refreshtoken.adapter.out.repository.RefreshTokenRepository
import hs.kr.entrydsm.user.domain.user.adapter.`in`.web.dto.request.UserLoginRequest
import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserInfo
import hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository.UserInfoRepository
import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserLoginUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.exception.PasswordNotValidException
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import hs.kr.entrydsm.user.global.security.jwt.JwtProperties
import hs.kr.entrydsm.user.global.security.jwt.JwtTokenProvider
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 로그인 서비스 클래스입니다.
 * 사용자 인증 및 JWT 토큰 발급 처리를 담당합니다.
 *
 * @property jwtTokenProvider JWT 토큰 제공자
 * @property passwordEncoder 비밀번호 암호화 인코더
 * @property queryUserPort 사용자 조회 포트
 * @property jwtProperties JWT 설정 프로퍼티
 * @property userInfoRepository 사용자 정보 저장소
 */
@Service
class UserLoginService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val queryUserPort: QueryUserPort,
    private val jwtProperties: JwtProperties,
    private val userInfoRepository: UserInfoRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
) : UserLoginUseCase {
    /**
     * 사용자 로그인을 처리하고 JWT 토큰을 반환합니다.
     * 전화번호와 비밀번호를 검증한 후 토큰을 발급하고 사용자 정보를 캐시합니다.
     *
     * @param request 로그인 요청 정보
     * @return 생성된 JWT 토큰 응답
     * @throws UserNotFoundException 사용자가 존재하지 않거나 비활성화된 경우
     * @throws PasswordNotValidException 비밀번호가 일치하지 않는 경우
     */
    @Transactional
    override fun login(request: UserLoginRequest): TokenResponse {
        val user = queryUserPort.findByPhoneNumber(request.phoneNumber) ?: throw UserNotFoundException

        if (!user.active) {
            throw UserNotFoundException
        }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordNotValidException
        }

        refreshTokenRepository.deleteById(user.id.toString())

        val tokenResponse = jwtTokenProvider.generateToken(user.id.toString(), user.role.toString())
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
