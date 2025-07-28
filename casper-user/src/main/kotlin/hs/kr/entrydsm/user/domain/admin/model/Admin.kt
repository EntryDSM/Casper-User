package hs.kr.entrydsm.user.domain.admin.model

import java.util.UUID

/**
 * 관리자 도메인 모델을 나타내는 데이터 클래스입니다.
 * 시스템 관리자의 인증 정보를 관리합니다.
 *
 * @property id 관리자 고유 식별자
 * @property adminId 관리자 로그인 ID
 * @property password 암호화된 비밀번호
 */
data class Admin(
    val id: UUID?,
    val adminId: String,
    val password: String,
)
