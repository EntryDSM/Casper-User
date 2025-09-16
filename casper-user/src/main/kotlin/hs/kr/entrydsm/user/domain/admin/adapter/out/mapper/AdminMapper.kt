package hs.kr.entrydsm.user.domain.admin.adapter.out.mapper

import hs.kr.entrydsm.user.domain.admin.adapter.out.AdminJpaEntity
import hs.kr.entrydsm.user.domain.admin.model.Admin
import hs.kr.entrydsm.user.global.mapper.GenericMapper
import org.mapstruct.Mapper

/**
 * [Admin] 도메인 모델과 [AdminJpaEntity] JPA 엔티티 간의 변환을 담당하는 MapStruct 매퍼 클래스입니다.
 */
@Mapper(componentModel = "spring")
abstract class AdminMapper : GenericMapper<AdminJpaEntity, Admin> {
    /**
     * 도메인 모델 [Admin]을 JPA 엔티티 [AdminJpaEntity]로 변환합니다.
     *
     * @param model 변환할 도메인 모델
     * @return 변환된 JPA 엔티티
     */
    abstract override fun toEntity(model: Admin): AdminJpaEntity

    /**
     * JPA 엔티티 [AdminJpaEntity]를 도메인 모델 [Admin]로 변환합니다.
     *
     * @param entity 변환할 JPA 엔티티 (nullable)
     * @return 변환된 도메인 모델 (nullable)
     */
    abstract override fun toModel(entity: AdminJpaEntity?): Admin?
}
