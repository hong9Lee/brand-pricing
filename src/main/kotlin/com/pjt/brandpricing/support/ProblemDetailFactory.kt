package com.pjt.brandpricing.support

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import java.net.URI

class ProblemDetailFactory {

    companion object {
        fun problemDetail(
            httpStatus: HttpStatus,
            code: String,
            request: HttpServletRequest,
            exception: Exception,
            property: Map<String, Any>? = null
        ): ProblemDetail {
            return problemDetail(httpStatus, code, request, exception.message, property)
        }

        fun problemDetail(
            httpStatus: HttpStatus,
            code: String,
            request: HttpServletRequest,
            message: String?,
            property: Map<String, Any>? = null
        ): ProblemDetail {
            val problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, message)
            problemDetail.instance = URI.create(request.requestURL.toString())
            problemDetail.title = code
            problemDetail.setType(URI.create(request.requestURI))
            property?.forEach { (key, value) -> problemDetail.setProperty(key, value) }

            return problemDetail
        }
    }
}
