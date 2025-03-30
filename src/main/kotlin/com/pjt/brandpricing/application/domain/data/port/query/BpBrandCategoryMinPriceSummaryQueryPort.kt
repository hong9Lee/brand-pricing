package com.pjt.brandpricing.application.domain.data.port.query

import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinMaxPriceSummaryModel
import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinPriceSummaryModel
import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinTotalPriceModel

interface BpBrandCategoryMinPriceSummaryQueryPort {
    fun findAllBrandCategoryMinPriceSummaries(): BpBrandCategoryMinPriceSummaryModel
    fun findAllBrandCategoryMinTotalPrice(): BpBrandCategoryMinTotalPriceModel
    fun findMinMaxPriceByCategoryName(categoryName: String): BpBrandCategoryMinMaxPriceSummaryModel
}
