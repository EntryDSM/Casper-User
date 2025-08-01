package hs.kr.entrydsm.user.domain.user.model

import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserRole
import java.time.LocalDateTime
import java.util.UUID

/**
 * 사용자 도메인 모델을 나타내는 데이터 클래스입니다.
 * 시스템의 핵심 사용자 정보와 관련된 비즈니스 로직을 포함합니다.
 * 불변성을 보장하는 데이터 클래스로 설계되었습니다.
 *
 * @property id 사용자 고유 식별자
 * @property phoneNumber 사용자 전화번호
 * @property phoneNumberHash 암호화 된 전화번호를 조회하기 위한 Hash 컬럼
 * @property password 암호화된 비밀번호
 * @property name 사용자 이름
 * @property isParent 학부모 여부
 * @property receiptCode 지원서 접수번호
 * @property role 사용자 역할
 * @property isActive 계정 활성화 상태
 * @property withdrawalAt 탈퇴 일시
 */
data class User(
    val id: UUID?,
    val phoneNumber: String,
    val phoneNumberHash: String,
    val password: String,
    val name: String,
    val isParent: Boolean,
    val receiptCode: Long?,
    val role: UserRole,
    val isActive: Boolean = true,
    val withdrawalAt: LocalDateTime? = null,
) {
    /**
     * 사용자의 비밀번호를 변경합니다.
     *
     * @param newPassword 새로운 비밀번호
     * @return 비밀번호가 변경된 User 인스턴스
     */
    fun changePassword(newPassword: String): User {
        return copy(password = newPassword)
    }

    /**
     * 사용자의 접수코드를 변경합니다.
     *
     * @param newReceiptCode 새로운 접수코드
     * @return 접수코드가 변경된 User 인스턴스
     */
    fun changeReceiptCode(newReceiptCode: Long): User {
        return copy(receiptCode = newReceiptCode)
    }

    /**
     * 사용자 계정을 탈퇴 처리합니다.
     * 계정을 비활성화하고 탈퇴 일시를 현재 시간으로 설정합니다.
     *
     * @return 탈퇴 처리된 User 인스턴스
     */
    fun withdraw(): User {
        return copy(
            isActive = false,
            withdrawalAt = LocalDateTime.now(),
        )
    }

    /**
     * 탈퇴한 계정을 재활성화합니다.
     * 계정을 활성화하고 탈퇴 일시를 초기화합니다.
     *
     * @return 재활성화된 User 인스턴스
     */
    fun reactivate(): User {
        return copy(
            isActive = true,
            withdrawalAt = null,
        )
    }
}
