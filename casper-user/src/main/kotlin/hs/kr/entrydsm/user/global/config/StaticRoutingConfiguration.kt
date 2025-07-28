package hs.kr.entrydsm.user.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 정적 리소스 라우팅을 설정하는 클래스입니다.
 */
@Configuration
class StaticRoutingConfiguration : WebMvcConfigurer {
    /**
     * 정적 리소스 핸들러를 추가합니다.
     *
     * @param registry 리소스 핸들러를 등록할 레지스트리
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("*/dist/**").addResourceLocations("classpath:/static/")
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/static/swagger-ui/")
    }
}
