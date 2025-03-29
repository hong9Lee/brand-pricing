package com.pjt.brandpricing.application.domain.data.port.command

import com.pjt.brandpricing.application.domain.BpBrand
import com.pjt.brandpricing.support.EntityId

interface BpBrandCommandPort {
    fun save(bpBrand: BpBrand): BpBrand
    fun findByBrandIdOrThrow(brandId: EntityId): BpBrand
}
