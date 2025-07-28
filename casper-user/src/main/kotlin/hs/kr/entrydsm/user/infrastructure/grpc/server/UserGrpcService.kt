package hs.kr.entrydsm.user.infrastructure.grpc.server

import hs.kr.entrydsm.casper.user.proto.UserServiceGrpcKt
import hs.kr.entrydsm.casper.user.proto.UserServiceProto
import hs.kr.entrydsm.user.domain.user.application.port.`in`.QueryUserByUUIDUseCase
import hs.kr.entrydsm.user.infrastructure.grpc.server.mapper.UserGrpcMapper
import io.grpc.Status
import io.grpc.StatusException
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID

/**
 * 사용자 관련 gRPC 서비스 구현 클래스입니다.
 * 다른 마이크로서비스와의 gRPC 통신을 통해 사용자 정보를 제공합니다.
 *
 * @property queryUserByUUIDUseCase UUID로 사용자 조회 유스케이스
 * @property userGrpcMapper gRPC 메시지 변환 매퍼
 */
@GrpcService
class UserGrpcService(
    private val queryUserByUUIDUseCase: QueryUserByUUIDUseCase,
    private val userGrpcMapper: UserGrpcMapper,
) : UserServiceGrpcKt.UserServiceCoroutineImplBase() {

    /**
     * 사용자 ID로 사용자 정보를 조회합니다.
     *
     * @param request 사용자 ID가 포함된 gRPC 요청
     * @return 사용자 정보 gRPC 응답
     * @throws StatusException UUID 형식이 잘못되었거나 서버 오류가 발생한 경우
     */
    override suspend fun getUserInfoByUserId(request: UserServiceProto.GetUserInfoRequest): UserServiceProto.GetUserInfoResponse {

        return try {
            val userId = UUID.fromString(request.userId)
            val userInfo = queryUserByUUIDUseCase.getUserById(userId)
            userGrpcMapper.toGetUserInfoResponse(userInfo)
        } catch (e: IllegalArgumentException) {
            throw StatusException(Status.INVALID_ARGUMENT.withDescription("Invalid UUID format"))
        } catch (e: Exception) {
            throw StatusException(Status.INTERNAL.withDescription("서버 오류"))
        }
    }

}
