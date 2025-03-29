package com.pjt.brandpricing.adapter.controller.advice

import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.support.ProblemDetailFactory
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApplicationExceptionAdvice {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.initDirectFieldAccess()
    }

    @ExceptionHandler(value = [ApplicationException::class])
    fun handleException(
        request: HttpServletRequest,
        exception: ApplicationException,
    ): ResponseEntity<ProblemDetail> {

        val problemDetail = ProblemDetailFactory.problemDetail(
            exception.status,
            exception.code,
            request,
            exception,
            exception.property
        )

        return ResponseEntity.status(exception.status).body(problemDetail)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupportedException(
        e: HttpRequestMethodNotSupportedException,
    ): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build()
    }

    @ExceptionHandler(value = [BindException::class, MethodArgumentNotValidException::class])
    @Override
    fun handleBindException(
        request: HttpServletRequest,
        exception: BindException,
    ): ResponseEntity<ProblemDetail> {

        val httpStatus = HttpStatus.BAD_REQUEST
        val problemDetail = ProblemDetailFactory.problemDetail(
            httpStatus,
            httpStatus.name,
            request,
            exception.bindingResult.allErrors[0].defaultMessage
        )

        return ResponseEntity.status(httpStatus).body(problemDetail)
    }

}
