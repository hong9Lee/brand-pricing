package com.pjt.brandpricing.infra.persistence.adapter.query

import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinMaxPriceSummaryModel
import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinPriceSummaryModel
import com.pjt.brandpricing.application.domain.data.out.BpBrandCategoryMinTotalPriceModel
import com.pjt.brandpricing.application.domain.data.port.query.BpBrandCategoryMinPriceSummaryQueryPort
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.infra.persistence.repository.query.BpBrandCategoryMinPriceSummaryQueryRepository
import org.springframework.stereotype.Component

@Component
class BpBrandCategoryMinSummaryQueryAdapter(
    private val bpBrandCategoryMinPriceSummaryQueryRepository: BpBrandCategoryMinPriceSummaryQueryRepository,
) : BpBrandCategoryMinPriceSummaryQueryPort {

    override fun findAllBrandCategoryMinPriceSummaries(): BpBrandCategoryMinPriceSummaryModel {
        val results = bpBrandCategoryMinPriceSummaryQueryRepository.findAllBrandCategoryPriceSummaries()

        val grouped = results
            .groupBy { it.categoryName }
            .map { (categoryName, items) ->
                val minItem = items.minByOrNull { it.minPrice.value }!!
                BpBrandCategoryMinPriceSummaryModel.BpBrandCategoryMinPriceSummaryData(
                    categoryName = categoryName,
                    brandName = minItem.brandName,
                    minPrice = minItem.minPrice
                )
            }

        return BpBrandCategoryMinPriceSummaryModel(summaries = grouped)
    }

    override fun findAllBrandCategoryMinTotalPrice(): BpBrandCategoryMinTotalPriceModel {
        return BpBrandCategoryMinTotalPriceModel.of(
            bpBrandCategoryMinPriceSummaryQueryRepository.findAllBrandCategoryPriceSummaries()
        )
    }

    override fun findMinMaxPriceByCategoryName(categoryName: String): BpBrandCategoryMinMaxPriceSummaryModel {
        val minResults = bpBrandCategoryMinPriceSummaryQueryRepository.findMinPriceByCategoryName(categoryName)
        val maxResults = bpBrandCategoryMinPriceSummaryQueryRepository.findMaxPriceByCategoryName(categoryName)

        if (minResults.isEmpty() && maxResults.isEmpty()) {
            throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_CATEGORY_PRODUCT)
        }

        return BpBrandCategoryMinMaxPriceSummaryModel(
            categoryName = categoryName,
            min = minResults.map {
                BpBrandCategoryMinMaxPriceSummaryModel.BpBrandCategoryMinMaxPriceSummaryData(
                    brandName = it.brandName,
                    price = it.price
                )
            },
            max = maxResults.map {
                BpBrandCategoryMinMaxPriceSummaryModel.BpBrandCategoryMinMaxPriceSummaryData(
                    brandName = it.brandName,
                    price = it.price
                )
            }
        )
    }
}
