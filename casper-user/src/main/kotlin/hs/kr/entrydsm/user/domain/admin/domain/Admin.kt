package hs.kr.entrydsm.user.domain.admin.domain

import hs.kr.entrydsm.user.global.entity.BaseUUIDEntity
import java.util.UUID
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "tbl_admin")
class Admin(
    id: UUID?,
    @Column(name = "admin_id", length = 15, nullable = false)
    val adminId: String,
    @Column(name = "password", length = 60, nullable = false)
    val password: String,
) : BaseUUIDEntity(id)
