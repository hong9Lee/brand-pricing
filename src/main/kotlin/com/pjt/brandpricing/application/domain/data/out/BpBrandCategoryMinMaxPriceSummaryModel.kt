package com.pjt.brandpricing.application.domain.data.out

import com.pjt.brandpricing.application.domain.BpPrice

data class BpBrandCategoryMinMaxPriceSummaryModel(
    val categoryName: String,
    val min: List<BpBrandCategoryMinMaxPriceSummaryData>,
    val max: List<BpBrandCategoryMinMaxPriceSummaryData>,
) {
    data class BpBrandCategoryMinMaxPriceSummaryData(
        val brandName: String,
        val price: BpPrice,
    )
}

