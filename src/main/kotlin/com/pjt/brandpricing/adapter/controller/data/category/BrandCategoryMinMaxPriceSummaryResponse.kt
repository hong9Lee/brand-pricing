package com.pjt.brandpricing.adapter.controller.data.category

import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinMaxPriceSummaryModel
import java.text.DecimalFormat

data class BrandCategoryMinMaxPriceSummaryResponse(
    val 카테고리: String,
    val 최저가: List<BpBrandCategoryMinMaxPriceSummaryResponseData>,
    val 최고가: List<BpBrandCategoryMinMaxPriceSummaryResponseData>,
) {
    data class BpBrandCategoryMinMaxPriceSummaryResponseData(
        val 브랜드: String,
        val 가격: String,
    )

    companion object {
        private val formatter = DecimalFormat("#,###")

        fun of(model: BpBrandCategoryMinMaxPriceSummaryModel): BrandCategoryMinMaxPriceSummaryResponse {
            return BrandCategoryMinMaxPriceSummaryResponse(
                카테고리 = model.categoryName,
                최저가 = model.min.map {
                    BpBrandCategoryMinMaxPriceSummaryResponseData(
                        브랜드 = it.brandName,
                        가격 = formatter.format(it.price.value)
                    )
                },
                최고가 = model.max.map {
                    BpBrandCategoryMinMaxPriceSummaryResponseData(
                        브랜드 = it.brandName,
                        가격 = formatter.format(it.price.value)
                    )
                }
            )
        }
    }
}

