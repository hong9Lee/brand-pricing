package com.pjt.brandpricing.application.domain.data.out

import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.infra.persistence.model.BpBrandCategoryMinPriceSummaryResult
import java.math.BigDecimal

data class BpBrandCategoryMinTotalPriceModel(
    val brandName: String,
    val categories: List<CategoryMinPrice>,
    val totalPrice: BigDecimal,
) {
    data class CategoryMinPrice(
        val categoryName: String,
        val minPrice: BigDecimal,
    )

    companion object {
        fun of(results: List<BpBrandCategoryMinPriceSummaryResult>): BpBrandCategoryMinTotalPriceModel {
            val brandToPrices = results.groupBy { it.brandName }
                .mapValues { entry ->
                    entry.value.sumOf { it.minPrice.value }
                }

            val cheapestBrand = brandToPrices.minByOrNull { it.value }
                ?: throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_BRAND)

            val categoryPrices = results
                .filter { it.brandName == cheapestBrand.key }
                .map {
                    CategoryMinPrice(
                        categoryName = it.categoryName,
                        minPrice = it.minPrice.value
                    )
                }

            return BpBrandCategoryMinTotalPriceModel(
                brandName = cheapestBrand.key,
                categories = categoryPrices,
                totalPrice = cheapestBrand.value
            )
        }
    }
}
