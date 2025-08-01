package hs.kr.entrydsm.user.domain.admin.adapter.`in`.web

import hs.kr.entrydsm.user.domain.admin.adapter.`in`.web.dto.request.AdminLoginRequest
import hs.kr.entrydsm.user.domain.admin.application.port.`in`.AdminLoginUseCase
import hs.kr.entrydsm.user.domain.admin.application.port.`in`.AdminTokenRefreshUseCase
import hs.kr.entrydsm.user.domain.admin.application.port.`in`.DeleteAllTableUseCase
import hs.kr.entrydsm.user.domain.admin.application.port.`in`.QueryAdminByUUIDUseCase
import hs.kr.entrydsm.user.global.document.admin.AdminApiDocument
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/**
 * 관리자 관련 HTTP 요청을 처리하는 REST 컨트롤러 클래스입니다.
 */
@RestController
@RequestMapping("/admin")
class AdminWebAdapter(
    private val adminLoginUseCase: AdminLoginUseCase,
    private val adminTokenRefreshUseCase: AdminTokenRefreshUseCase,
    private val deleteAllTableUseCase: DeleteAllTableUseCase,
    private val queryAdminByUUIDUseCase: QueryAdminByUUIDUseCase,
) : AdminApiDocument {
    /**
     * 관리자 로그인을 처리합니다.
     */
    @PostMapping("/auth")
    override fun login(
        @RequestBody @Valid
        adminLoginRequest: AdminLoginRequest,
    ): TokenResponse = adminLoginUseCase.login(adminLoginRequest)

    /**
     * 관리자 토큰을 갱신합니다.
     */
    @PutMapping("/auth")
    override fun tokenRefresh(
        @RequestHeader("X-Refresh-Token") refreshToken: String,
    ): TokenResponse = adminTokenRefreshUseCase.refresh(refreshToken)

    /**
     * 모든 테이블을 삭제합니다.
     */
    @DeleteMapping("/auth")
    override fun deleteAllTable() = deleteAllTableUseCase.deleteAllTables()

    /**
     * UUID로 관리자 정보를 조회합니다.
     */
    @GetMapping("/{adminId}")
    override fun findAdminById(
        @PathVariable adminId: UUID,
    ) = queryAdminByUUIDUseCase.queryByUUID(adminId)
}
