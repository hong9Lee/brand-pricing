package com.pjt.brandpricing.infra.persistence.model

import com.pjt.brandpricing.application.domain.BpPrice

data class BpBrandCategoryMinMaxPriceSummaryResult(
    val brandName: String,
    val categoryName: String,
    val price: BpPrice
)

