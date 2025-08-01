package hs.kr.entrydsm.user.infrastructure.grpc.server.mapper

import hs.kr.entrydsm.casper.admin.proto.AdminServiceProto
import hs.kr.entrydsm.casper.user.proto.UserServiceProto
import hs.kr.entrydsm.user.infrastructure.grpc.server.dto.InternalAdminResponse
import hs.kr.entrydsm.user.infrastructure.grpc.server.dto.InternalUserResponse
import hs.kr.entrydsm.user.domain.user.adapter.out.domain.UserRole
import org.springframework.stereotype.Component

/**
 * 사용자 정보를 gRPC 프로토콜 버퍼와 매핑하는 매퍼 클래스입니다.
 * 내부 DTO와 gRPC 메시지 간의 변환을 담당합니다.
 */
@Component
class UserGrpcMapper {
    
    /**
     * InternalUserResponse 객체를 gRPC 사용자 정보 응답으로 변환합니다.
     *
     * @param userResponse 변환할 사용자 응답 DTO
     * @return gRPC 사용자 정보 응답
     */
    fun toGetUserInfoResponse(userResponse: InternalUserResponse): UserServiceProto.GetUserInfoResponse {
        return UserServiceProto.GetUserInfoResponse.newBuilder()
            .setId(userResponse.id.toString())
            .setPhoneNumber(userResponse.phoneNumber)
            .setName(userResponse.name)
            .setIsParent(userResponse.isParent)
            .setRole(toProtoUserRole(userResponse.role))
            .build()
    }

    /**
     * InternalAdminResponse 객체를 gRPC 사용자 정보 응답으로 변환합니다.
     *
     * @param adminResponse 변환할 사용자 응답 DTO
     * @return gRPC 사용자 정보 응답
     */
    fun toGetAdminIdResponse(adminResponse: InternalAdminResponse): AdminServiceProto.GetAdminIdResponse {
        return AdminServiceProto.GetAdminIdResponse.newBuilder()
            .setAdminId(adminResponse.id.toString())
            .build()
    }
    
    /**
     * 도메인 사용자 역할을 gRPC 프로토콜 사용자 역할로 변환합니다.
     *
     * @param userRole 변환할 도메인 사용자 역할
     * @return gRPC 프로토콜 사용자 역할
     */
    private fun toProtoUserRole(userRole: UserRole): UserServiceProto.UserRole{
        return when(userRole){
            UserRole.ROOT -> UserServiceProto.UserRole.ROOT
            UserRole.USER -> UserServiceProto.UserRole.USER
            UserRole.ADMIN -> UserServiceProto.UserRole.ADMIN
        }
    }
}
