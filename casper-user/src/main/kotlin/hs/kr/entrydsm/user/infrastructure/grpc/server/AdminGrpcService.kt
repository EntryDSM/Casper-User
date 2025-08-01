package hs.kr.entrydsm.user.infrastructure.grpc.server

import hs.kr.entrydsm.casper.admin.proto.AdminServiceGrpcKt
import hs.kr.entrydsm.casper.admin.proto.AdminServiceProto
import hs.kr.entrydsm.user.domain.admin.application.port.`in`.QueryAdminByUUIDUseCase
import hs.kr.entrydsm.user.infrastructure.grpc.server.mapper.UserGrpcMapper
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID

@GrpcService
class AdminGrpcService(
    private val queryAdminByUUIDUseCase: QueryAdminByUUIDUseCase,
    private val userGrpcMapper: UserGrpcMapper
) : AdminServiceGrpcKt.AdminServiceCoroutineImplBase() {

    override suspend fun getAdminByUUID(request: AdminServiceProto.GetAdminIdRequest): AdminServiceProto.GetAdminIdResponse {
        val adminId = UUID.fromString(request.adminId)
        val currentAdminId = queryAdminByUUIDUseCase.queryByUUID(adminId)
        return userGrpcMapper.toGetAdminIdResponse(currentAdminId)
    }
}