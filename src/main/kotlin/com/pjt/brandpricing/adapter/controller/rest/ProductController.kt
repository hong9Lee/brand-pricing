package com.pjt.brandpricing.adapter.controller.rest

import com.pjt.brandpricing.adapter.controller.UrlConstants
import com.pjt.brandpricing.adapter.controller.data.product.CreateProductRequest
import com.pjt.brandpricing.adapter.controller.data.product.CreateProductResponse
import com.pjt.brandpricing.adapter.controller.data.product.UpdateProductRequest
import com.pjt.brandpricing.adapter.controller.data.product.UpdateProductResponse
import com.pjt.brandpricing.application.domain.data.`in`.CreateProductCommand
import com.pjt.brandpricing.application.domain.data.`in`.UpdateProductCommand
import com.pjt.brandpricing.application.service.command.BpProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val bpProductService: BpProductService,
) {

    @PostMapping(UrlConstants.상품_생성)
    fun create(@RequestBody request: CreateProductRequest): ResponseEntity<CreateProductResponse> {
        return ResponseEntity.ok(
            CreateProductResponse.of(
                bpProductService.create(CreateProductCommand.of(request))
            )
        )
    }

    @PatchMapping(UrlConstants.상품_업데이트)
    fun update(@RequestBody request: UpdateProductRequest): ResponseEntity<UpdateProductResponse> {
        return ResponseEntity.ok(
            UpdateProductResponse.of(
                bpProductService.update(UpdateProductCommand.of(request))
            )
        )
    }
}

