package com.pjt.brandpricing.fake

import com.pjt.brandpricing.application.domain.BpBrandCategoryPriceSummary
import com.pjt.brandpricing.application.domain.data.port.command.BpBrandCategoryPriceSummaryCommandPort
import com.pjt.brandpricing.support.EntityId
import java.util.concurrent.ConcurrentHashMap

class FakeBpBrandCategoryPriceSummaryCommandPort : BpBrandCategoryPriceSummaryCommandPort {
    private val summaryMap = ConcurrentHashMap<Pair<EntityId, EntityId>, BpBrandCategoryPriceSummary>()

    override fun save(summary: BpBrandCategoryPriceSummary): BpBrandCategoryPriceSummary {
        summaryMap[Pair(summary.brandId, summary.categoryId)] = summary
        return summary
    }

    override fun findByBrandIdAndCategoryId(brandId: EntityId, categoryId: EntityId): BpBrandCategoryPriceSummary? {
        return summaryMap[Pair(brandId, categoryId)]
    }

    fun getByBrandIdAndCategoryId(brandId: EntityId, categoryId: EntityId): BpBrandCategoryPriceSummary? {
        return summaryMap[Pair(brandId, categoryId)]
    }

    override fun deleteByBrandIdAndCategoryId(brandId: EntityId, categoryId: EntityId) {
        summaryMap.remove(Pair(brandId, categoryId))
    }
}
