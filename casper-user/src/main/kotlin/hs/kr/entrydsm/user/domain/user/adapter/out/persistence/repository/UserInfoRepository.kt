package hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository

import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserInfo
import org.springframework.data.repository.CrudRepository

/**
 * 사용자 인증 정보를 위한 Redis 저장소 인터페이스입니다.
 * Spring Data Redis를 통해 사용자 인증 캐시 데이터를 관리합니다.
 */
interface UserInfoRepository : CrudRepository<UserInfo, String>
