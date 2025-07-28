package hs.kr.entrydsm.user.domain.user.adapter.out.domain

/**
 * 사용자 권한을 정의하는 열거형 클래스입니다.
 * 시스템 내에서 사용자의 접근 권한을 구분하는 데 사용됩니다.
 */
enum class UserRole {
    /** 최고 관리자 권한 */
    ROOT,

    /** 일반 사용자 권한 */
    USER,

    /** 관리자 권한 */
    ADMIN,
}
