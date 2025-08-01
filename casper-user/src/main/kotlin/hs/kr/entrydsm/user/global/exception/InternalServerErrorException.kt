package hs.kr.entrydsm.user.global.exception

import hs.kr.entrydsm.user.global.error.exception.CasperException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * 내부 서버 오류가 발생했을 때 사용하는 예외입니다.
 * 예상치 못한 서버 측 오류가 발생한 경우 사용됩니다.
 */
object InternalServerErrorException : CasperException(
    ErrorCode.INTERNAL_SERVER_ERROR,
)
