package com.pjt.brandpricing.infra.persistence.adapter.command

import com.pjt.brandpricing.application.domain.BpBrand
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.application.domain.data.port.command.BpBrandCommandPort
import com.pjt.brandpricing.infra.persistence.entity.BpBrandEntity
import com.pjt.brandpricing.infra.persistence.repository.command.BpBrandRepository
import com.pjt.brandpricing.support.EntityId
import org.springframework.stereotype.Component

@Component
class BpBrandCommandAdapter(
    private val bpBrandRepository: BpBrandRepository,
) : BpBrandCommandPort {

    override fun save(bpBrand: BpBrand): BpBrand {
        return bpBrandRepository.save(BpBrandEntity.of(bpBrand)).toDomain()
    }

    override fun findByBrandIdOrThrow(brandId: EntityId): BpBrand {
        return bpBrandRepository.findByBrandId(brandId)?.toDomain()
            ?: throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_BRAND)
    }
}
