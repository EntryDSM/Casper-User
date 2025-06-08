package hs.kr.entrydsm.user.domain.admin.facade

import hs.kr.entrydsm.user.domain.admin.domain.Admin
import hs.kr.entrydsm.user.domain.admin.domain.repository.AdminRepository
import hs.kr.entrydsm.user.domain.admin.exception.AdminNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AdminFacade(
    private val adminRepository: AdminRepository,
) {
    fun getCurrentUser(): Admin {
        val adminId = SecurityContextHolder.getContext().authentication.name
        return adminRepository.findByIdOrNull(UUID.fromString(adminId)) ?: throw AdminNotFoundException
    }

    fun getUserById(adminId: String): Admin = adminRepository.findByIdOrNull(UUID.fromString(adminId)) ?: throw AdminNotFoundException
}
