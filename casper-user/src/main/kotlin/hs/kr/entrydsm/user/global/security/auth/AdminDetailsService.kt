package hs.kr.entrydsm.user.global.security.auth

import hs.kr.entrydsm.user.domain.admin.domain.Admin
import hs.kr.entrydsm.user.domain.admin.exception.AdminUnauthorizedException
import hs.kr.entrydsm.user.domain.admin.facade.AdminFacade
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AdminDetailsService(
    private val adminFacade: AdminFacade,
) : UserDetailsService {
    override fun loadUserByUsername(adminId: String?): UserDetails {
        val admin: Admin = adminId?.let { adminFacade.getUserById(it) } ?: throw AdminUnauthorizedException
        return AuthDetails(admin.adminId)
    }
}
