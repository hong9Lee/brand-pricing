package com.pjt.brandpricing.application.domain.data.out

import com.pjt.brandpricing.application.domain.BpPrice

data class BpBrandCategoryMinPriceSummaryModel(
    val summaries: List<BpBrandCategoryMinPriceSummaryData>
) {

    data class BpBrandCategoryMinPriceSummaryData(
        val categoryName: String,
        val brandName: String,
        val minPrice: BpPrice
    )
}
