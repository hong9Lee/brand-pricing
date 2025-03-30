package com.pjt.brandpricing.application.domain.data.port.command

import com.pjt.brandpricing.application.domain.BpBrandCategoryPriceSummary
import com.pjt.brandpricing.support.EntityId

interface BpBrandCategoryPriceSummaryCommandPort {
    fun save(brandCategoryPriceSummary: BpBrandCategoryPriceSummary): BpBrandCategoryPriceSummary
    fun findByBrandIdAndCategoryId(brandId: EntityId, categoryId: EntityId): BpBrandCategoryPriceSummary?
}
