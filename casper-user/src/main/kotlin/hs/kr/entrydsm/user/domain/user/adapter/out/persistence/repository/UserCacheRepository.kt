package hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository

import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserCache
import org.springframework.data.repository.CrudRepository
import java.util.UUID

/**
 * 사용자 캐시를 위한 Redis 저장소 인터페이스입니다.
 * Spring Data Redis를 통해 사용자 캐시 데이터의 관리를 담당합니다.
 */
interface UserCacheRepository : CrudRepository<UserCache, UUID>
