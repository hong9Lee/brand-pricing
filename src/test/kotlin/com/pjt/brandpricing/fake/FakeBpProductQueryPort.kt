package com.pjt.brandpricing.fake

import com.pjt.brandpricing.application.domain.BpProducts
import com.pjt.brandpricing.application.domain.data.port.query.BpProductQueryPort
import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.support.EntityId

class FakeBpProductQueryPort(
    private val productCommandPort: FakeBpProductCommandPort,
    private val productMappingPort: FakeBpProductMappingPort,
) : BpProductQueryPort {
    override fun findAllByBrandIdAndCategoryId(
        brandId: EntityId,
        categoryId: EntityId,
        isActive: Boolean?,
        productStatus: ProductStatus?,
    ): BpProducts {
        val productIds = productMappingPort.savedMappings
            .filter { it.brandId == brandId && it.categoryId == categoryId && it.isActive == (isActive ?: true) }
            .map { it.productId }
            .toSet()

        val products = productCommandPort.savedProducts
            .filter { productIds.contains(it.productId) }

        return BpProducts(products)
    }
}

