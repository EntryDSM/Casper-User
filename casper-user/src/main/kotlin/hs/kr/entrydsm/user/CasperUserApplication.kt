package hs.kr.entrydsm.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Casper User 애플리케이션의 메인 클래스입니다.
 * 사용자 관리 및 인증 서비스를 제공합니다.
 */
@SpringBootApplication
class CasperUserApplication

/**
 * 애플리케이션의 진입점 함수입니다.
 * Spring Boot 애플리케이션을 시작합니다.
 */
fun main(args: Array<String>) {
    runApplication<CasperUserApplication>(*args)
}
