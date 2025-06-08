package hs.kr.entrydsm.user.domain.user.domain

import hs.kr.entrydsm.user.global.entity.BaseUUIDEntity
import java.util.UUID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity(name = "tbl_user")
class User(
    id: UUID?,
    @Column(columnDefinition = "char(11)", nullable = false, unique = true)
    val phoneNumber: String,
    @Column(columnDefinition = "char(60)", nullable = false)
    var password: String,
    @Column(columnDefinition = "char(5)", nullable = false)
    val name: String,
    @Column(columnDefinition = "bit(1) default 1", nullable = false)
    val isParent: Boolean,
    @Column(name = "receipt_code", nullable = true)
    var receiptCode: Long?,
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole,
) : BaseUUIDEntity(id) {
    fun changePassword(password: String) {
        this.password = password
    }

    fun changeReceiptCode(receiptCode: Long) {
        this.receiptCode = receiptCode
    }

    fun toUserCache(): UserCache {
        return UserCache(
            id = id,
            phoneNumber = phoneNumber,
            name = name,
            isParent = isParent,
            receiptCode = receiptCode,
            role = role,
            ttl = 60 * 10,
        )
    }
}
