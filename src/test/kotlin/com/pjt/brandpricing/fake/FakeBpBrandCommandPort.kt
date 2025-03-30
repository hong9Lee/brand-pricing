package com.pjt.brandpricing.fake

import com.pjt.brandpricing.application.domain.BpBrand
import com.pjt.brandpricing.application.domain.data.port.command.BpBrandCommandPort
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.support.EntityId

class FakeBpBrandCommandPort : BpBrandCommandPort {
    private val savedBrands = mutableListOf<BpBrand>()

    override fun save(bpBrand: BpBrand): BpBrand {
        savedBrands.removeIf { it.brandId == bpBrand.brandId }
        savedBrands.add(bpBrand)
        return bpBrand
    }

    override fun findByBrandIdOrThrow(brandId: EntityId): BpBrand {
        return savedBrands.find { it.brandId == brandId }
            ?: throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_BRAND)
    }

    override fun existsByBrandId(brandId: EntityId): Boolean {
        return savedBrands.any { it.brandId == brandId }
    }
}
