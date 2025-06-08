package hs.kr.entrydsm.user.domain.user.domain.repository

import hs.kr.entrydsm.user.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByPhoneNumber(phoneNumber: String): User?

    fun existsByPhoneNumber(phoneNumber: String): Boolean

    fun deleteById(id: UUID?)
}
