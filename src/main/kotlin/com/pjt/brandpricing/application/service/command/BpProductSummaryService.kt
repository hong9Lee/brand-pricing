package com.pjt.brandpricing.application.service.command

import com.pjt.brandpricing.application.domain.BpBrandCategoryPriceSummary
import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.application.domain.data.port.command.BpBrandCategoryPriceSummaryCommandPort
import com.pjt.brandpricing.application.domain.data.port.query.BpProductQueryPort
import com.pjt.brandpricing.support.EntityId
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BpProductSummaryService(
    private val bpProductQueryPort: BpProductQueryPort,
    private val bpBrandCategoryPriceSummaryCommandPort: BpBrandCategoryPriceSummaryCommandPort,
) {
    private val log = KotlinLogging.logger {}

    fun recalculateSummary(brandId: EntityId, categoryId: EntityId) {
        log.info { "Recalculating bpProductSummary brandId: $brandId categoryId: $categoryId" }

        val products = bpProductQueryPort.findAllByBrandIdAndCategoryId(brandId, categoryId)
        val minProducts = products.findMinPriceProducts()
        val maxProducts = products.findMaxPriceProducts()

        val summary = bpBrandCategoryPriceSummaryCommandPort.findByBrandIdAndCategoryId(brandId, categoryId)
        if (summary == null) {
            saveNewProductSummary(brandId, categoryId, minProducts, maxProducts)
            return
        }

        updateProductSummary(summary, minProducts, maxProducts)
    }

    private fun saveNewProductSummary(
        brandId: EntityId,
        categoryId: EntityId,
        minProducts: List<BpProduct>,
        maxProducts: List<BpProduct>,
    ) {
        bpBrandCategoryPriceSummaryCommandPort.save(
            BpBrandCategoryPriceSummary(
                brandId = brandId,
                categoryId = categoryId,
                minPrice = minProducts.first().price,
                minPriceProductIds = minProducts.map { it.productId },
                maxPrice = maxProducts.first().price,
                maxPriceProductIds = maxProducts.map { it.productId }
            )
        )
    }

    private fun updateProductSummary(
        summary: BpBrandCategoryPriceSummary,
        minProducts: List<BpProduct>,
        maxProducts: List<BpProduct>,
    ) {
        summary.updateMinPrice(
            minProducts = minProducts
        )
        summary.updateMaxPrice(
            maxProducts = maxProducts
        )
        bpBrandCategoryPriceSummaryCommandPort.save(summary)
    }
}
