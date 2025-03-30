package com.pjt.brandpricing.application.service.query

import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinMaxPriceSummaryModel
import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinPriceSummaryModel
import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinTotalPriceModel
import com.pjt.brandpricing.application.domain.data.port.query.BpBrandCategoryMinPriceSummaryQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BpBrandCategoryMinPriceSummaryQueryService(
    private val bpBrandCategoryMinPriceSummaryQueryPort: BpBrandCategoryMinPriceSummaryQueryPort,
) {

    fun getMinPriceSummary(): BpBrandCategoryMinPriceSummaryModel {
        return bpBrandCategoryMinPriceSummaryQueryPort.findAllBrandCategoryMinPriceSummaries()
    }

    fun getMinTotalPrice(): BpBrandCategoryMinTotalPriceModel {
        return bpBrandCategoryMinPriceSummaryQueryPort.findAllBrandCategoryMinTotalPrice()
    }

    fun getMinMaxPriceSummary(categoryName: String): BpBrandCategoryMinMaxPriceSummaryModel {
        return bpBrandCategoryMinPriceSummaryQueryPort.findMinMaxPriceByCategoryName(categoryName)
    }
}
