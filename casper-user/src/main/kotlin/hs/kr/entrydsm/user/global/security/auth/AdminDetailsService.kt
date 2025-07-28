package hs.kr.entrydsm.user.global.security.auth

import hs.kr.entrydsm.user.domain.admin.application.port.`in`.AdminFacadeUseCase
import hs.kr.entrydsm.user.domain.admin.exception.AdminUnauthorizedException
import hs.kr.entrydsm.user.domain.admin.model.Admin
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * Spring Security 관리자 인증을 위한 사용자 상세 정보 로딩 서비스 클래스입니다.
 */
@Service
class AdminDetailsService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
) : UserDetailsService {
    /**
     * 관리자 ID로 관리자 정보를 로드합니다.
     */
    override fun loadUserByUsername(adminId: String?): UserDetails {
        val admin: Admin = adminId?.let { adminFacadeUseCase.getUserById(it) } ?: throw AdminUnauthorizedException
        return AuthDetails(admin.adminId)
    }
}
