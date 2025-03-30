package com.pjt.brandpricing.adapter.controller.rest

import com.pjt.brandpricing.adapter.controller.UrlConstants
import com.pjt.brandpricing.adapter.controller.data.category.BrandCategoryMinMaxPriceSummaryResponse
import com.pjt.brandpricing.adapter.controller.data.category.BrandCategoryMinPriceSummaryResponse
import com.pjt.brandpricing.adapter.controller.data.category.BrandCategoryMinTotalPriceResponse
import com.pjt.brandpricing.application.service.query.BpBrandCategoryMinPriceSummaryQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BrandCategoryPriceSummaryController(
    private val bpBrandCategoryMinPriceSummaryQueryService: BpBrandCategoryMinPriceSummaryQueryService,
) {

    @GetMapping(UrlConstants.최저가격_카테고리_조회)
    fun getCategorySummary(): ResponseEntity<BrandCategoryMinPriceSummaryResponse> {
        return ResponseEntity.ok(
            BrandCategoryMinPriceSummaryResponse.of(
                bpBrandCategoryMinPriceSummaryQueryService.getMinPriceSummary()
            )
        )
    }

    @GetMapping(UrlConstants.최저가격_브랜드_조회)
    fun getBrandCategoryMinTotalPrice(): ResponseEntity<BrandCategoryMinTotalPriceResponse> {
        return ResponseEntity.ok(
            BrandCategoryMinTotalPriceResponse.of(
                bpBrandCategoryMinPriceSummaryQueryService.getMinTotalPrice()
            )
        )
    }

    @GetMapping(UrlConstants.최고최저_카테고리_조회)
    fun getCategoryMinMaxSummary(
        @PathVariable categoryName: String,
    ): ResponseEntity<BrandCategoryMinMaxPriceSummaryResponse> {
        return ResponseEntity.ok(
            BrandCategoryMinMaxPriceSummaryResponse.of(
                bpBrandCategoryMinPriceSummaryQueryService.getMinMaxPriceSummary(categoryName)
            )
        )
    }
}
