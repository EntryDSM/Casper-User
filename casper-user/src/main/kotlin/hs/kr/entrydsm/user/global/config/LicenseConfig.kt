package hs.kr.entrydsm.user.global.config

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.PosixFilePermission

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
            val filePath = Paths.get(PATH)
            URL(licenseFileURl).openStream()
                .use { inputStream ->
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
                }

            // 파일 권한 설정 (읽기/쓰기 권한)
            val permissions =
                setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.GROUP_READ,
                    PosixFilePermission.OTHERS_READ,
                )
            Files.setPosixFilePermissions(filePath, permissions)
        } catch (e: IOException) {
            println("LICENSE DEBUG: 라이선스 파일 다운로드 실패 - 에러: ${e.message}")
            e.printStackTrace()
        } catch (e: UnsupportedOperationException) {
            // Windows 등 POSIX를 지원하지 않는 시스템에서는 권한 설정 무시
            println("File permission setting is not supported on this platform")
        }
    }

    /**
     * 라이센스 파일 경로 상수
     */
    companion object {
        /**
         * 라이센스 파일 저장 경로
         */
        private const val PATH = "/tmp/V61290000000_IDS_01_PROD_AES_license.dat"
    }
}
