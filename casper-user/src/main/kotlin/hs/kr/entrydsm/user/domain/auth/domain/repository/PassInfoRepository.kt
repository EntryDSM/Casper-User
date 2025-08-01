package hs.kr.entrydsm.user.domain.auth.domain.repository

import hs.kr.entrydsm.user.domain.auth.domain.PassInfo
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface PassInfoRepository : CrudRepository<PassInfo, String> {
    fun findByPhoneNumber(phoneNumber: String): Optional<PassInfo>

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
