package hs.kr.entrydsm.user.global.utils.pass

import hs.kr.entrydsm.user.domain.auth.exception.InvalidUrlException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RedirectUrlChecker {
    @Value("\${pass.base-url}")
    private lateinit var BASE_URL: String

    fun checkRedirectUrl(redirectUrl: String) {
        if (!redirectUrl.startsWith(BASE_URL)) {
            throw InvalidUrlException
        }
    }
}
