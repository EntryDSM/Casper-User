package hs.kr.entrydsm.user.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.entrydsm.user.global.error.GlobalExceptionFilter
import hs.kr.entrydsm.user.global.security.jwt.JwtFilter
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class FilterConfig(
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        val jwtFilter = JwtFilter() 
        val globalExceptionFilter = GlobalExceptionFilter(objectMapper)

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(globalExceptionFilter, JwtFilter::class.java)
    }
}
