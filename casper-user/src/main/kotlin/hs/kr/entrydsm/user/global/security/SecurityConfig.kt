package hs.kr.entrydsm.user.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

/**
 * Spring Security 설정 클래스입니다.
 * 애플리케이션의 보안 정책과 인증/인가 규칙을 정의합니다.
 *
 * @property objectMapper JSON 직렬화/역직렬화를 위한 ObjectMapper
 */
@Configuration
class SecurityConfig(
    private val objectMapper: ObjectMapper,
) {
    /**
     * Spring Security 필터 체인을 구성합니다.
     * HTTP 보안 설정 및 경로별 접근 권한을 정의합니다.
     *
     * @param http HttpSecurity 객체
     * @return 구성된 SecurityFilterChain
     */
    @Bean
    protected fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/").permitAll()
                    .requestMatchers(HttpMethod.PATCH, "/user/password").permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/user").permitAll()
                    .requestMatchers("/user/verify/popup").permitAll()
                    .requestMatchers(HttpMethod.GET, "/user/verify/info").permitAll()
                    .requestMatchers(HttpMethod.POST, "/user/auth").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/user/auth").permitAll()
                    .requestMatchers(HttpMethod.POST, "/admin/auth").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/swagger-resources/**").permitAll()
                    .requestMatchers("/webjars/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/admin/auth").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/user").hasRole("ROOT")
                    .requestMatchers(HttpMethod.GET, "/admin/").hasRole("ROOT")
                    .anyRequest().authenticated()
            }
            .with(FilterConfig(objectMapper)) { }

        return http.build()
    }

    /**
     * 비밀번호 암호화를 위한 BCrypt 인코더를 설정합니다.
     *
     * @return BCryptPasswordEncoder 인스턴스
     */
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
