package com.pjt.brandpricing.infra.persistence.adapter.query

import com.pjt.brandpricing.application.domain.BpProducts
import com.pjt.brandpricing.application.domain.data.port.query.BpProductQueryPort
import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.infra.persistence.repository.query.BpProductQueryRepository
import com.pjt.brandpricing.support.EntityId
import org.springframework.stereotype.Component

@Component
class BpProductQueryAdapter(
    private val bpProductQueryRepository: BpProductQueryRepository,
) : BpProductQueryPort {

    override fun findAllByBrandIdAndCategoryId(
        brandId: EntityId,
        categoryId: EntityId,
        isActive: Boolean?,
        productStatus: ProductStatus?,
    ): BpProducts {
        val products = bpProductQueryRepository.findAllByBrandIdAndCategoryId(
            brandId = brandId,
            categoryId = categoryId,
            isActive = isActive ?: true,
            productStatus = productStatus ?: ProductStatus.DISPLAY,
        ).map { it.toDomain() }

        return BpProducts(products)
    }
}
