package com.pjt.brandpricing.application.domain.data.port.command

import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.support.EntityId

interface BpProductCommandPort {
    fun save(product: BpProduct): BpProduct
    fun getByProductIdOrThrow(productId: EntityId): BpProduct
}
