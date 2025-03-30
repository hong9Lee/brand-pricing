package com.pjt.brandpricing.application.domain

import com.pjt.brandpricing.support.EntityId

class BpBrandCategoryPriceSummary(
    val seq: Long? = null,
    val brandId: EntityId,
    val categoryId: EntityId,
    var minPrice: BpPrice,
    var minPriceProductIds: List<EntityId>,
    var maxPrice: BpPrice,
    var maxPriceProductIds: List<EntityId>,
) {

    fun updateMinPrice(minProducts: List<BpProduct>) {
        this.minPrice = minProducts.first().price
        this.minPriceProductIds = minProducts.map { it.productId }
    }

    fun updateMaxPrice(maxProducts: List<BpProduct>) {
        this.maxPrice = maxProducts.first().price
        this.maxPriceProductIds = maxProducts.map { it.productId }
    }
}
