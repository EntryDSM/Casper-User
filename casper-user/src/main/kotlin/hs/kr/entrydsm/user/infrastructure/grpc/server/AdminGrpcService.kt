package hs.kr.entrydsm.user.infrastructure.grpc.server

import hs.kr.entrydsm.casper.admin.proto.AdminServiceGrpcKt
import hs.kr.entrydsm.casper.admin.proto.AdminServiceProto
import hs.kr.entrydsm.user.domain.admin.application.port.`in`.QueryAdminByUUIDUseCase
import hs.kr.entrydsm.user.infrastructure.grpc.server.mapper.UserGrpcMapper
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID

/**
 * Admin 관련 gRPC 서비스를 제공하는 클래스입니다.
 * Admin 정보 조회 기능을 gRPC 프로토콜로 제공합니다.
 */
@GrpcService
class AdminGrpcService(
    private val queryAdminByUUIDUseCase: QueryAdminByUUIDUseCase,
    private val userGrpcMapper: UserGrpcMapper
) : AdminServiceGrpcKt.AdminServiceCoroutineImplBase() {

    /**
     * 어드민 ID로 어드민 ID를 조회합니다.
     *
     * @param request 어드민 ID가 포함된 gRPC 요청
     * @return 어드민 ID gRPC 응답
     * @throws AdminNotFoundException admin을 찾을 수 없을 때
     */
    override suspend fun getAdminByUUID(request: AdminServiceProto.GetAdminIdRequest): AdminServiceProto.GetAdminIdResponse {
        val adminId = UUID.fromString(request.adminId)
        val currentAdminId = queryAdminByUUIDUseCase.queryByUUID(adminId)
        return userGrpcMapper.toGetAdminIdResponse(currentAdminId)
    }
}