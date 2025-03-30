package com.pjt.brandpricing.adapter.controller.data.category

import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinTotalPriceModel
import java.text.DecimalFormat

data class BrandCategoryMinTotalPriceResponse(
    val 최저가: MinTotalPrice,
) {
    data class MinTotalPrice(
        val 브랜드: String,
        val 카테고리: List<CategoryMinPrice>,
        val 총액: String,
    )

    data class CategoryMinPrice(
        val 카테고리: String,
        val 가격: String,
    )

    companion object {
        private val formatter = DecimalFormat("#,###")

        fun of(model: BpBrandCategoryMinTotalPriceModel): BrandCategoryMinTotalPriceResponse {
            return BrandCategoryMinTotalPriceResponse(
                최저가 = MinTotalPrice(
                    브랜드 = model.brandName,
                    카테고리 = model.categories.map {
                        CategoryMinPrice(
                            카테고리 = it.categoryName,
                            가격 = formatter.format(it.minPrice)
                        )
                    },
                    총액 = formatter.format(model.totalPrice)
                )
            )
        }
    }
}
