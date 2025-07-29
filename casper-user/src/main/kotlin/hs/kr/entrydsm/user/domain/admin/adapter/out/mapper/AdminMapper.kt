package hs.kr.entrydsm.user.domain.admin.adapter.out.mapper

import hs.kr.entrydsm.user.domain.admin.adapter.out.AdminJpaEntity
import hs.kr.entrydsm.user.domain.admin.model.Admin
import hs.kr.entrydsm.user.global.mapper.GenericMapper
import org.mapstruct.Mapper

/**
 * Admin 도메인 모델과 AdminJpaEntity 간의 변환을 담당하는 매퍼 클래스입니다.
 */
@Mapper(componentModel = "spring")
abstract class AdminMapper : GenericMapper<AdminJpaEntity, Admin> {
    abstract override fun toEntity(model: Admin): AdminJpaEntity

    abstract override fun toModel(entity: AdminJpaEntity?): Admin?
}
