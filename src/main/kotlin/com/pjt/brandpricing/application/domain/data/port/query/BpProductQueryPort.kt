package com.pjt.brandpricing.application.domain.data.port.query

import com.pjt.brandpricing.application.domain.BpProducts
import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.support.EntityId

interface BpProductQueryPort {
    fun findAllByBrandIdAndCategoryId(
        brandId: EntityId,
        categoryId: EntityId,
        isActive: Boolean? = true,
        productStatus: ProductStatus? = ProductStatus.DISPLAY,
    ): BpProducts
}
