package com.pjt.brandpricing.infra.persistence.adapter.command

import com.pjt.brandpricing.application.domain.BpProductMapping
import com.pjt.brandpricing.application.domain.data.port.command.BpProductMappingPort
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.infra.persistence.entity.BpProductMappingEntity
import com.pjt.brandpricing.infra.persistence.repository.command.BpProductMappingRepository
import com.pjt.brandpricing.support.EntityId
import org.springframework.stereotype.Component

@Component
class BpProductMappingAdapter(
    private val productMappingRepository: BpProductMappingRepository,
) : BpProductMappingPort {

    override fun save(mapping: BpProductMapping): BpProductMapping {
        return productMappingRepository.save(BpProductMappingEntity.of(mapping)).toDomain()
    }

    override fun findByProductId(productId: EntityId): BpProductMapping {
        return productMappingRepository.findByProductId(productId)?.toDomain()
            ?: throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_PRODUCT)
    }

    override fun updateActiveByProductId(productId: EntityId, isActive: Boolean) {
        productMappingRepository.updateIsActiveByProductId(productId, isActive)
    }
}
