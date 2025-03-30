package com.pjt.brandpricing.fake

import com.pjt.brandpricing.application.domain.BpProductMapping
import com.pjt.brandpricing.application.domain.data.port.command.BpProductMappingPort
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.support.EntityId

class FakeBpProductMappingPort : BpProductMappingPort {
    val savedMappings = mutableListOf<BpProductMapping>()

    override fun save(mapping: BpProductMapping): BpProductMapping {
        savedMappings.add(mapping)
        return mapping
    }

    override fun findByProductId(productId: EntityId): BpProductMapping {
        return savedMappings.find { it.productId == productId }
            ?: throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_PRODUCT_MAPPING)
    }
}

