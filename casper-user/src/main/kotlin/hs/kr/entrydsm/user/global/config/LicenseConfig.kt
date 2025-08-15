package hs.kr.entrydsm.user.global.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * 패스 인증 라이센스 파일을 다운로드하고 설정하는 클래스입니다.
 */
@Configuration
class LicenseConfig(
    @Value("\${pass.license_file_url}")
    val licenseFileURl: String,
) {
    /**
     * 애플리케이션 시작 시 라이센스 파일을 다운로드합니다.
     * 기존 파일이 있어도 최신 버전으로 덮어씁니다.
     */
    @PostConstruct
    fun initialize() {
        try {
            URL(licenseFileURl).openStream()
                .use { inputStream ->
                    Files.copy(inputStream, Paths.get(PATH), StandardCopyOption.REPLACE_EXISTING)
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 라이센스 파일 경로 상수
     */
    companion object {
        /**
         * 라이센스 파일 저장 경로
         */
        private const val PATH = "./V61290000000_IDS_01_PROD_AES_license.dat"
    }
}
