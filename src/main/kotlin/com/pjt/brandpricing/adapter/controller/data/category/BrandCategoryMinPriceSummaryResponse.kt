package com.pjt.brandpricing.adapter.controller.data.category

import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinPriceSummaryModel
import java.text.DecimalFormat

data class BrandCategoryMinPriceSummaryResponse(
    val result: List<BpBrandCategoryMinPriceSummaryResponseData>,
    val 총액: String,
) {
    data class BpBrandCategoryMinPriceSummaryResponseData(
        val 카테고리: String,
        val 브랜드: String,
        val 가격: String,
    )

    companion object {
        private val formatter = DecimalFormat("#,###")

        fun of(model: BpBrandCategoryMinPriceSummaryModel): BrandCategoryMinPriceSummaryResponse {
            val responseData = model.summaries.map {
                BpBrandCategoryMinPriceSummaryResponseData(
                    카테고리 = it.categoryName,
                    브랜드 = it.brandName,
                    가격 = formatter.format(it.minPrice.value)
                )
            }
            val total = model.summaries.sumOf { it.minPrice.value }
            return BrandCategoryMinPriceSummaryResponse(
                result = responseData,
                총액 = formatter.format(total)
            )
        }
    }
}
