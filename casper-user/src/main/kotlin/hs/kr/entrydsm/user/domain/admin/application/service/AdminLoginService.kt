package hs.kr.entrydsm.user.domain.admin.application.service

import hs.kr.entrydsm.user.domain.admin.adapter.`in`.web.dto.request.AdminLoginRequest
import hs.kr.entrydsm.user.domain.admin.application.port.`in`.AdminLoginUseCase
import hs.kr.entrydsm.user.domain.admin.application.port.out.QueryAdminPort
import hs.kr.entrydsm.user.domain.admin.application.port.out.SaveAdminPort
import hs.kr.entrydsm.user.domain.admin.exception.AdminNotFoundException
import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserInfo
import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserRole
import hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository.UserInfoRepository
import hs.kr.entrydsm.user.domain.user.exception.PasswordNotValidException
import hs.kr.entrydsm.user.global.security.jwt.JwtProperties
import hs.kr.entrydsm.user.global.security.jwt.JwtTokenProvider
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 관리자 로그인 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
class AdminLoginService(
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userInfoRepository: UserInfoRepository,
    private val jwtProperties: JwtProperties,
    private val adminQueryAdminPort: QueryAdminPort,
    private val adminSaveAdminPort: SaveAdminPort,
) : AdminLoginUseCase {
    /**
     * 관리자 로그인을 처리하고 JWT 토큰을 반환합니다.
     */
    @Transactional
    override fun login(adminLoginRequest: AdminLoginRequest): TokenResponse {
        val admin = adminQueryAdminPort.findByAdminId(adminLoginRequest.adminId) ?: throw AdminNotFoundException

        if (!passwordEncoder.matches(adminLoginRequest.password, admin.password)) {
            throw PasswordNotValidException
        }
        val tokenResponse = jwtTokenProvider.generateToken(admin.id.toString(), UserRole.ADMIN.toString())
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
