package hs.kr.entrydsm.user.domain.refreshtoken.adapter.out.repository

import hs.kr.entrydsm.user.domain.refreshtoken.adapter.out.RefreshToken
import org.springframework.data.repository.CrudRepository

/**
 * 리프레시 토큰에 대한 Redis 액세스를 담당하는 리포지토리 인터페이스입니다.
 */
interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    /**
     * 토큰 값으로 리프레시 토큰을 조회합니다.
     */
    fun findByToken(token: String): RefreshToken?
}
