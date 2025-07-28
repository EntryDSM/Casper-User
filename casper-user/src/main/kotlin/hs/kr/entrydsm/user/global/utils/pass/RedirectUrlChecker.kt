package hs.kr.entrydsm.user.global.utils.pass

import hs.kr.entrydsm.user.domain.auth.exception.InvalidUrlException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * 리다이렉트 URL의 유효성을 검사하는 클래스입니다.
 */
@Component
class RedirectUrlChecker {
    /**
     * Pass 인증의 기본 URL
     */
    @Value("\${pass.base-url}")
    private lateinit var baseUrl: String

    /**
     * 리다이렉트 URL이 허용된 기본 URL로 시작하는지 확인합니다.
     */
    fun checkRedirectUrl(redirectUrl: String) {
        if (!redirectUrl.startsWith(baseUrl)) {
            throw InvalidUrlException
        }
    }
}
