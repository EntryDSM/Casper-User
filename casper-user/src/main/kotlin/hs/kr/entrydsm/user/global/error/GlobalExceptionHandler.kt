package hs.kr.entrydsm.user.global.error

import hs.kr.entrydsm.user.global.error.exception.EquusException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import kotlin.collections.get

/**
 * 애플리케이션의 전역 예외 처리를 담당하는 클래스입니다.
 * 모든 컨트롤러에서 발생하는 예외를 처리하여 일관된 오류 응답을 제공합니다.
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * Equus 애플리케이션의 커스텀 예외를 처리합니다.
     *
     * @param e EquusException 인스턴스
     * @return 에러 코드에 따른 응답 엔티티
     */
    @ExceptionHandler(EquusException::class)
    fun handlingEquusException(e: EquusException): ResponseEntity<ErrorResponse> {
        val code = e.errorCode
        return ResponseEntity(
            ErrorResponse(code.status, code.message),
            HttpStatus.valueOf(code.status),
        )
    }

    /**
     * 유효성 검증 실패 예외를 처리합니다.
     *
     * @param e MethodArgumentNotValidException 인스턴스
     * @return 400 에러 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validatorExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                400,
                e.bindingResult.allErrors[0].defaultMessage,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }
}
