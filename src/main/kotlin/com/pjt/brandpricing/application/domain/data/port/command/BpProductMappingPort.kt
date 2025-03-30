package com.pjt.brandpricing.application.domain.data.port.command

import com.pjt.brandpricing.application.domain.BpProductMapping
import com.pjt.brandpricing.support.EntityId

interface BpProductMappingPort {
    fun save(mapping: BpProductMapping): BpProductMapping
    fun findByProductId(productId: EntityId): BpProductMapping
    fun updateActiveByProductId(productId: EntityId, isActive: Boolean)
}
