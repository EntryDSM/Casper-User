package hs.kr.entrydsm.user.domain.auth.exception

import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode

/**
 * OkCert 연결이 유효하지 않을 때 발생하는 예외입니다.
 */
object InvalidOkCertConnectException : EquusException(
    ErrorCode.INVALID_OKCERT_CONNECTION,
)
