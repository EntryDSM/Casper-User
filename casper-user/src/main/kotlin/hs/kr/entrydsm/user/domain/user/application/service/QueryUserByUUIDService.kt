package hs.kr.entrydsm.user.domain.user.application.service

import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserCache
import hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository.UserCacheRepository
import hs.kr.entrydsm.user.domain.user.application.port.`in`.QueryUserByUUIDUseCase
import hs.kr.entrydsm.user.domain.user.application.port.out.QueryUserPort
import hs.kr.entrydsm.user.domain.user.exception.UserNotFoundException
import hs.kr.entrydsm.user.infrastructure.grpc.server.dto.InternalUserResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * UUID로 사용자 조회 서비스 클래스입니다.
 * 고유 식별자를 통한 사용자 정보 조회 및 캐시 관리를 처리합니다.
 *
 * @property queryUserPort 사용자 조회 포트
 * @property userCacheRepository 사용자 캐시 저장소
 */
@Service
class QueryUserByUUIDService(
    private val queryUserPort: QueryUserPort,
    private val userCacheRepository: UserCacheRepository,
) : QueryUserByUUIDUseCase {
    /**
     * UUID를 이용하여 사용자 정보를 조회합니다.
     * 캐시가 없는 경우 새로 생성하여 저장하고, 내부 시스템용 응답 형태로 변환합니다.
     *
     * @param userId 조회할 사용자의 UUID
     * @return 내부 시스템용 사용자 정보 응답
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     */
    @Transactional(readOnly = true)
    override fun getUserById(userId: UUID): InternalUserResponse {
        val user = queryUserPort.findById(userId) ?: throw UserNotFoundException

        if (!userCacheRepository.existsById(userId)) {
            val userCache = UserCache.from(user)
            userCacheRepository.save(userCache)
        }
        return InternalUserResponse(
            id = user.id!!,
            phoneNumber = user.phoneNumber,
            name = user.name,
            isParent = user.isParent,
            receiptCode = user.receiptCode,
            role = user.role,
        )
    }
}
