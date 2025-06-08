package hs.kr.entrydsm.user.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val objectMapper: ObjectMapper
) {

    @Bean
    protected fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { } // 필요 시 CORS 설정 추가
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
                    .requestMatchers(HttpMethod.DELETE, "/admin/auth").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/user").hasRole("ROOT")
                    .requestMatchers(HttpMethod.GET, "/admin/").hasRole("ROOT")
                    .anyRequest().authenticated()
            }

            .with(FilterConfig(objectMapper)) { }

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
