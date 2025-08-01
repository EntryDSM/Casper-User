package hs.kr.entrydsm.user.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.entrydsm.user.global.error.GlobalExceptionFilter
import hs.kr.entrydsm.user.global.security.jwt.JwtFilter
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

/**
 * 보안 필터 체인을 설정하는 클래스입니다.
 */
@Component
class FilterConfig(
    private val objectMapper: ObjectMapper,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    /**
     * 보안 필터를 설정합니다.
     * JWT 필터와 전역 예외 필터를 Spring Security 필터 체인에 추가합니다.
     *
     * @param http HTTP 보안 설정 객체
     */
    override fun configure(http: HttpSecurity) {
        val jwtFilter = JwtFilter()
        val globalExceptionFilter = GlobalExceptionFilter(objectMapper)

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(globalExceptionFilter, JwtFilter::class.java)
    }
}
