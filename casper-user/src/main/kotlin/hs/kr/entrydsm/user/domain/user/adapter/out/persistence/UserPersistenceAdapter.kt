package hs.kr.entrydsm.user.domain.user.adapter.out.persistence

import hs.kr.entrydsm.user.domain.user.adapter.out.mapper.UserMapper
import hs.kr.entrydsm.user.domain.user.adapter.out.persistence.repository.UserRepository
import hs.kr.entrydsm.user.domain.user.application.port.out.UserPort
import hs.kr.entrydsm.user.domain.user.model.User
import hs.kr.entrydsm.user.global.utils.encryption.EncryptionUtil
import hs.kr.entrydsm.user.global.utils.encryption.HashUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

/**
 * 사용자 데이터의 영속성 처리를 담당하는 어댑터 클래스입니다.
 * 헥사고날 아키텍처의 Driven Adapter 역할을 하며, 도메인 계층의 UserPort를 구현합니다.
 *
 * @property userRepository JPA 기반 사용자 데이터 저장소
 * @property userMapper 도메인 모델과 JPA 엔티티 간 변환 매퍼
 * @property encryptionUtil 민감한 데이터 암호화 유틸리티
 */
@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val encryptionUtil: EncryptionUtil,
) : UserPort {
    /**
     * ID로 사용자를 조회합니다.
     *
     * @param id 조회할 사용자 ID
     * @return 조회된 사용자 도메인 모델 (없으면 null)
     */
    override fun findById(id: UUID): User? {
        return userRepository.findByIdOrNull(id)
            ?.let { userMapper.toModel(it) }
    }

    /**
     * 전화번호로 사용자를 조회합니다.
     * 전화번호를 암호화하여 데이터베이스에서 조회합니다.
     *
     * @param phoneNumber 조회할 전화번호
     * @return 조회된 사용자 도메인 모델 (없으면 null)
     */
    override fun findByPhoneNumber(phoneNumber: String): User? {
        val phoneNumberHash = HashUtil.sha256(phoneNumber)
        return userRepository.findByPhoneNumberHash(phoneNumberHash)
            ?.let { userMapper.toModel(it) }
    }

    /**
     * 전화번호로 사용자 존재 여부를 확인합니다.
     *
     * @param phoneNumber 확인할 전화번호
     * @return 사용자 존재 여부
     */
    override fun existsByPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberHash = HashUtil.sha256(phoneNumber)
        return userRepository.existsByPhoneNumberHash(phoneNumberHash)
    }

    /**
     * 사용자 정보를 저장합니다.
     *
     * @param user 저장할 사용자 도메인 모델
     * @return 저장된 사용자 도메인 모델
     */
    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val savedEntity = userRepository.save(entity)
        return userMapper.toModelNotNull(savedEntity)
    }

    /**
     * ID로 사용자를 삭제합니다.
     *
     * @param userId 삭제할 사용자 ID
     */
    override fun deleteById(userId: UUID) {
        userRepository.deleteById(userId)
    }

    /**
     * 모든 사용자를 삭제합니다.
     * 관리자의 전체 데이터 초기화 시에만 사용됩니다.
     */
    override fun deleteAll() {
        userRepository.deleteAll()
    }

    /**
     * 지정된 일수보다 오래된 탈퇴 사용자를 조회합니다.
     *
     * @param days 기준 일수
     * @return 삭제 대상 사용자 목록
     */
    override fun findWithdrawnUsersOlderThan(days: Long): List<User> {
        val cutoffDate = LocalDateTime.now().minusDays(days)
        return userRepository.findAllByActiveFalseAndWithdrawalAtBefore(cutoffDate)
            .map { userMapper.toModelNotNull(it) }
    }
}
