package hs.kr.entrydsm.user.global.document.admin

import hs.kr.entrydsm.user.domain.admin.adapter.`in`.web.dto.request.AdminLoginRequest
import hs.kr.entrydsm.user.infrastructure.grpc.server.dto.InternalAdminResponse
import hs.kr.entrydsm.user.global.utils.token.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import java.util.UUID

/**
 * 관리자 API 문서화를 위한 인터페이스입니다.
 */
@Tag(name = "Admin", description = "관리자 API")
interface AdminApiDocument {
    /**
     * 관리자 로그인을 처리합니다.
     */
    @Operation(
        summary = "관리자 로그인",
        description = "관리자 ID와 비밀번호로 로그인하여 JWT 토큰을 발급받습니다. 성공 시 UserInfo도 Redis에 저장됩니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "로그인 성공",
            content = arrayOf(Content(schema = Schema(implementation = TokenResponse::class))),
        ),
        ApiResponse(
            responseCode = "401",
            description = "비밀번호가 일치하지 않음 - Invalid User Password",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 관리자 ID - Admin Not Found",
            content = arrayOf(Content()),
        ),
    )
    fun login(
        @RequestBody @Valid adminLoginRequest: AdminLoginRequest,
    ): TokenResponse

    /**
     * 관리자 토큰을 갱신합니다.
     */
    @Operation(
        summary = "관리자 토큰 갱신",
        description = "Refresh Token을 사용하여 새로운 Access Token을 발급받습니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "토큰 갱신 성공",
            content = arrayOf(Content(schema = Schema(implementation = TokenResponse::class))),
        ),
        ApiResponse(
            responseCode = "401",
            description = "유효하지 않거나 만료된 토큰 - Invalid Token | Expired Token",
            content = arrayOf(Content()),
        ),
    )
    fun tokenRefresh(
        @Parameter(description = "갱신할 Refresh Token", required = true)
        @RequestHeader("X-Refresh-Token") refreshToken: String,
    ): TokenResponse

    @Operation(
        summary = "모든 테이블 삭제 (Kafka 메시지 발송)",
        description = "Kafka를 통해 모든 테이블 삭제 메시지를 발송합니다. 실제 삭제는 Consumer에서 처리됩니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "삭제 메시지 발송 완료",
            content = arrayOf(Content()),
        ),
        ApiResponse(
            responseCode = "401",
            description = "관리자 권한 없음 - Admin UnAuthorized",
            content = arrayOf(Content()),
        ),
    )
    /**
     * 모든 테이블 삭제 메시지를 발송합니다.
     */
    @SecurityRequirement(name = "bearerAuth")
    fun deleteAllTable()

    /**
     * UUID로 관리자 정보를 조회합니다.
     */
    @Operation(
        summary = "관리자 정보 조회",
        description = "UUID로 특정 관리자의 정보를 조회합니다.",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = arrayOf(Content(schema = Schema(implementation = InternalAdminResponse::class))),
        ),
        ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 관리자 - Admin Not Found",
            content = arrayOf(Content()),
        ),
    )
    fun findAdminById(
        @Parameter(description = "조회할 관리자의 UUID", required = true)
        @PathVariable adminId: UUID,
    ): InternalAdminResponse
}
