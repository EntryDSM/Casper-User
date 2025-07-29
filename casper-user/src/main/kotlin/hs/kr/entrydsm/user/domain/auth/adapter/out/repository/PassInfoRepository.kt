package hs.kr.entrydsm.user.domain.auth.adapter.out.repository

import hs.kr.entrydsm.user.domain.auth.adapter.out.PassInfo
import org.springframework.data.repository.CrudRepository
import java.util.Optional

/**
 * Pass 인증 정보를 위한 Redis 저장소 인터페이스입니다.
 * Spring Data Redis를 통해 Pass 인증 데이터의 관리를 담당합니다.
 */
interface PassInfoRepository : CrudRepository<PassInfo, String> {

}
