package hs.kr.entrydsm.user.domain.auth.adapter.out.repository

import hs.kr.entrydsm.user.domain.auth.adapter.out.PassInfo
import org.springframework.data.repository.CrudRepository
import java.util.Optional

/**
 * Pass 인증 정보를 위한 Redis 저장소 인터페이스입니다.
 * Spring Data Redis를 통해 Pass 인증 데이터의 관리를 담당합니다.
 */
interface PassInfoRepository : CrudRepository<PassInfo, String> {
    /**
     * 전화번호로 Pass 인증 정보를 조회합니다.
     *
     * @param phoneNumber 조회할 전화번호
     * @return Pass 인증 정보 Optional
     */
    fun findByPhoneNumber(phoneNumber: String): Optional<PassInfo>

    /**
     * 전화번호로 Pass 인증 정보 존재 여부를 확인합니다.
     *
     * @param phoneNumber 확인할 전화번호
     * @return Pass 인증 정보 존재 여부
     */
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
