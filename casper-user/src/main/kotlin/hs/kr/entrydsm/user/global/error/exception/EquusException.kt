package hs.kr.entrydsm.user.global.error.exception

import java.lang.RuntimeException

/**
 * Equus 애플리케이션의 모든 커스텀 예외의 기본 클래스입니다.
 * 에러 코드를 포함하여 일관된 예외 처리를 제공합니다.
 *
 * @property errorCode 발생한 오류의 에러 코드
 */
abstract class EquusException(
    val errorCode: ErrorCode,
) : RuntimeException()
