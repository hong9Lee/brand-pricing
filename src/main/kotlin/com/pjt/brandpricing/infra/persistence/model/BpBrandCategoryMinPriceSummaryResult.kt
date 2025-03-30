package com.pjt.brandpricing.infra.persistence.model

import com.pjt.brandpricing.application.domain.BpPrice

data class BpBrandCategoryMinPriceSummaryResult(
    val categoryName: String,
    val brandName: String,
    val minPrice: BpPrice,
)
