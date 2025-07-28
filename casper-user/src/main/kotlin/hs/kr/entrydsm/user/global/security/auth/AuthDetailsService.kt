package hs.kr.entrydsm.user.global.security.auth

import hs.kr.entrydsm.user.domain.user.application.port.`in`.UserFacadeUseCase
import hs.kr.entrydsm.user.domain.user.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * Spring Security 인증을 위한 사용자 상세 정보 로딩 서비스 클래스입니다.
 */
@Service
class AuthDetailsService(
    private val userFacadeUseCase: UserFacadeUseCase,
) : UserDetailsService {
    /**
     * 전화번호로 사용자 정보를 로드합니다.
     */
    override fun loadUserByUsername(phoneNumber: String?): UserDetails {
        val user: User? = phoneNumber?.let { userFacadeUseCase.getUserByPhoneNumber(it) }
        return AuthDetails(user!!.phoneNumber)
    }
}
