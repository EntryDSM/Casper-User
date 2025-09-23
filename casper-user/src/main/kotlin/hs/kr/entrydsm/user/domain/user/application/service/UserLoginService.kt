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
        println("=== 로그인 디버깅 시작 ===")
        println("요청 전화번호: ${request.phoneNumber}")
        println("요청 비밀번호: ${request.password}")
        
        val user = queryUserPort.findByPhoneNumber(request.phoneNumber)
        println("DB에서 조회된 사용자: $user")
        
        if (user == null) {
            println("사용자를 찾을 수 없음!")
            throw UserNotFoundException
        }
        
        println("사용자 활성 상태: ${user.active}")
        if (!user.active) {
            println("비활성 사용자!")
            throw UserNotFoundException
        }

        println("비밀번호 검증 시작...")
        val passwordMatches = passwordEncoder.matches(request.password, user.password)
        println("비밀번호 일치 여부: $passwordMatches")
        println("저장된 비밀번호 해시: ${user.password}")
        
        if (!passwordMatches) {
            println("비밀번호 불일치!")
            throw PasswordNotValidException
        }
        
        println("로그인 성공, 토큰 생성 중...")
        println("=== 로그인 디버깅 끝 ===")

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
