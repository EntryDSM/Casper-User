package hs.kr.entrydsm.user.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * 스케줄링 기능을 활성화하는 설정 클래스입니다.
 * Spring의 @Scheduled 어노테이션을 사용한 작업을 가능하게 합니다.
 */
@Configuration
@EnableScheduling
class EnableScheduling
