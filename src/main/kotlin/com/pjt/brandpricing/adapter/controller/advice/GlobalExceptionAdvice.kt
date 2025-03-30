package com.pjt.brandpricing.adapter.controller.advice

import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.support.ProblemDetailFactory
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionAdvice {

    private val log = KotlinLogging.logger {}

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.initDirectFieldAccess()
    }

    /**
     * 도메인 예외
     */
    @ExceptionHandler(ApplicationException::class)
    fun handleApplicationException(
        request: HttpServletRequest,
        exception: ApplicationException,
    ): ResponseEntity<ProblemDetail> {
        log.warn { "[ApplicationException] ${exception.message}" }

        val problemDetail = ProblemDetailFactory.problemDetail(
            exception.status,
            exception.code,
            request,
            exception,
            exception.property
        )
        return ResponseEntity.status(exception.status).body(problemDetail)
    }

    /**
     * 요청 Body JSON 파싱 에러 (400)
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        request: HttpServletRequest,
        exception: HttpMessageNotReadableException,
    ): ResponseEntity<ProblemDetail> {
        log.warn { "[HttpMessageNotReadableException] ${exception.message}" }

        val httpStatus = HttpStatus.BAD_REQUEST
        val problemDetail = ProblemDetailFactory.problemDetail(
            httpStatus,
            httpStatus.name,
            request,
            "요청 형식이 올바르지 않습니다. 요청 JSON을 확인해주세요. ${exception.message}"
        )
        return ResponseEntity.status(httpStatus).body(problemDetail)
    }

    /**
     * Validation 실패 (400)
     */
    @ExceptionHandler(value = [BindException::class, MethodArgumentNotValidException::class])
    fun handleBindException(
        request: HttpServletRequest,
        exception: BindException,
    ): ResponseEntity<ProblemDetail> {
        log.warn { "[BindException] ${exception.message}" }

        val httpStatus = HttpStatus.BAD_REQUEST
        val problemDetail = ProblemDetailFactory.problemDetail(
            httpStatus,
            httpStatus.name,
            request,
            exception.bindingResult.allErrors[0].defaultMessage
        )
        return ResponseEntity.status(httpStatus).body(problemDetail)
    }

    /**
     * 지원하지 않는 HTTP 메서드 (405)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupportedException(
        e: HttpRequestMethodNotSupportedException,
    ): ResponseEntity<Unit> {
        log.warn { "[MethodNotSupportedException] ${e.message}" }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build()
    }

    /**
     * 그 외 모든 에러 (500)
     */
    @ExceptionHandler(Exception::class)
    fun handleAllException(
        request: HttpServletRequest,
        exception: Exception,
    ): ResponseEntity<ProblemDetail> {
        log.error(exception) { "[UnhandledException] ${exception.message}" }

        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        val problemDetail = ProblemDetailFactory.problemDetail(
            httpStatus,
            httpStatus.name,
            request,
            exception
        )
        return ResponseEntity.status(httpStatus).body(problemDetail)
    }
}
