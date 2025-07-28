package hs.kr.entrydsm.user.domain.user.adapter.out.mapper

import hs.kr.entrydsm.user.domain.user.adapter.out.UserJpaEntity
import hs.kr.entrydsm.user.domain.user.model.User
import hs.kr.entrydsm.user.global.mapper.GenericMapper
import org.mapstruct.Mapper
import org.mapstruct.Mapping

/**
 * User 도메인 모델과 UserJpaEntity 간의 변환을 담당하는 매퍼 클래스입니다.
 * MapStruct를 사용하여 도메인 계층과 인프라스트럭처 계층 간의 데이터 변환을 처리합니다.
 */
@Mapper
abstract class UserMapper : GenericMapper<UserJpaEntity, User> {

    @Mapping(target = "changePassword", ignore = true)
    @Mapping(target = "changeReceiptCode", ignore = true)
    abstract override fun toEntity(model: User): UserJpaEntity

    abstract override fun toModel(entity: UserJpaEntity?): User?
    abstract override fun toModelNotNull(entity: UserJpaEntity): User
}
