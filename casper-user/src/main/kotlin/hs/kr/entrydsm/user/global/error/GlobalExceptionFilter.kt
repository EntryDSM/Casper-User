package hs.kr.entrydsm.user.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.entrydsm.user.global.error.exception.EquusException
import hs.kr.entrydsm.user.global.error.exception.ErrorCode
import io.sentry.Sentry
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * 전역 예외 처리 필터 클래스입니다.
 * Spring Security 필터 체인에서 발생하는 예외를 처리하여 일관된 오류 응답을 제공합니다.
 *
 * @property objectMapper JSON 직렬화를 위한 ObjectMapper
 */
class GlobalExceptionFilter(
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {
    /**
     * 필터 체인에서 발생하는 예외를 처리합니다.
     * EquusException과 일반 Exception을 구분하여 처리하고, Sentry로 예외를 추적합니다.
     *
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param filterChain 필터 체인
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: EquusException) {
            Sentry.captureException(e)
            writerErrorCode(response, e.errorCode)
        } catch (e: Exception) {
            e.printStackTrace()
            Sentry.captureException(e)
            writerErrorCode(response, ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * 에러 코드를 HTTP 응답으로 작성합니다.
     *
     * @param response HTTP 응답 객체
     * @param errorCode 발생한 에러 코드
     * @throws IOException 응답 작성 중 IO 오류 발생 시
     */
    @Throws(IOException::class)
    private fun writerErrorCode(
        response: HttpServletResponse,
        errorCode: ErrorCode,
    ) {
        val errorResponse = ErrorResponse(errorCode.status, errorCode.message)
        response.status = errorCode.status
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}
