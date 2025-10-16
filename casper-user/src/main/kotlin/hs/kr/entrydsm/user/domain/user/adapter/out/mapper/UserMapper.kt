package hs.kr.entrydsm.user.domain.user.adapter.out.mapper

import hs.kr.entrydsm.user.domain.user.adapter.out.UserJpaEntity
import hs.kr.entrydsm.user.domain.user.model.User
import hs.kr.entrydsm.user.global.mapper.GenericMapper
import org.springframework.stereotype.Component

/**
 * User 도메인 모델과 UserJpaEntity 간의 변환을 담당하는 매퍼 클래스입니다.
 * 수동 구현으로 모든 필드를 명시적으로 매핑합니다.
 */
@Component
class UserMapper : GenericMapper<UserJpaEntity, User> {
    
    override fun toEntity(model: User): UserJpaEntity {
        return UserJpaEntity(
            id = model.id,
            phoneNumber = model.phoneNumber,
            phoneNumberHash = model.phoneNumberHash,
            password = model.password,
            name = model.name,
            isParent = model.isParent,
            receiptCode = model.receiptCode,
            role = model.role,
            active = model.active,
            withdrawalAt = model.withdrawalAt
        )
    }

    override fun toModel(entity: UserJpaEntity?): User? {
        if (entity == null) return null
        return User(
            id = entity.id,
            phoneNumber = entity.phoneNumber,
            phoneNumberHash = entity.phoneNumberHash,
            password = entity.password,
            name = entity.name,
            isParent = entity.isParent,
            receiptCode = entity.receiptCode,
            role = entity.role,
            active = entity.active,
            withdrawalAt = entity.withdrawalAt
        )
    }

    override fun toModelNotNull(entity: UserJpaEntity): User {
        return User(
            id = entity.id,
            phoneNumber = entity.phoneNumber,
            phoneNumberHash = entity.phoneNumberHash,
            password = entity.password,
            name = entity.name,
            isParent = entity.isParent,
            receiptCode = entity.receiptCode,
            role = entity.role,
            active = entity.active,
            withdrawalAt = entity.withdrawalAt
        )
    }
}
