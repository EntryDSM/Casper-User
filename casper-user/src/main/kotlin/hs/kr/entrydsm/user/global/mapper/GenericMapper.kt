package hs.kr.entrydsm.user.global.mapper

/**
 * 엔티티와 도메인 모델 간의 변환을 위한 제네릭 매퍼 인터페이스입니다.
 * MapStruct와 함께 사용하여 일관된 변환 로직을 제공합니다.
 *
 * @param E 엔티티 타입
 * @param D 도메인 모델 타입
 */
interface GenericMapper<E, D> {
    /**
     * 도메인 모델을 엔티티로 변환합니다.
     *
     * @param model 변환할 도메인 모델
     * @return 변환된 엔티티
     */
    fun toEntity(model: D): E

    /**
     * 엔티티를 도메인 모델로 변환합니다.
     *
     * @param entity 변환할 엔티티 (null 가능)
     * @return 변환된 도메인 모델 (null 가능)
     */
    fun toModel(entity: E?): D?

    /**
     * 엔티티를 도메인 모델로 변환합니다.
     * null이 아닌 엔티티를 받아 null이 아닌 도메인 모델을 반환합니다.
     *
     * @param entity 변환할 엔티티 (null이 아님)
     * @return 변환된 도메인 모델 (null이 아님)
     */
    fun toModelNotNull(entity: E): D
}
