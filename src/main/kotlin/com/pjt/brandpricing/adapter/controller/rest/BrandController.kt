package com.pjt.brandpricing.adapter.controller.rest

import com.pjt.brandpricing.adapter.controller.UrlConstants
import com.pjt.brandpricing.adapter.controller.data.brand.CreateBrandRequest
import com.pjt.brandpricing.adapter.controller.data.brand.CreateBrandResponse
import com.pjt.brandpricing.adapter.controller.data.brand.UpdateBrandRequest
import com.pjt.brandpricing.adapter.controller.data.brand.UpdateBrandResponse
import com.pjt.brandpricing.application.service.command.BrandService
import com.pjt.brandpricing.application.domain.data.`in`.UpdateBrandCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BrandController(
    private val brandService: BrandService,
) {

    @PostMapping(UrlConstants.브랜드_생성)
    fun create(@RequestBody request: CreateBrandRequest): ResponseEntity<CreateBrandResponse> {
        return ResponseEntity.ok(
            CreateBrandResponse.of(
                brandService.create(request.brandName)
            )
        )
    }

    @PatchMapping(UrlConstants.브랜드_업데이트)
    fun update(
        @RequestBody request: UpdateBrandRequest,
    ): ResponseEntity<UpdateBrandResponse> {
        return ResponseEntity.ok(
            UpdateBrandResponse.of(
                brandService.update(
                    updateBrandCommand = UpdateBrandCommand.of(request)
                )
            )
        )
    }
}
