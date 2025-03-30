package com.pjt.brandpricing.infra.persistence.adapter.command

import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.application.domain.data.port.command.BpProductCommandPort
import com.pjt.brandpricing.infra.persistence.entity.BpProductEntity
import com.pjt.brandpricing.infra.persistence.repository.command.BpProductRepository
import com.pjt.brandpricing.support.EntityId
import org.springframework.stereotype.Component

@Component
class BpBpProductCommandAdapter(
    private val productRepository: BpProductRepository,
) : BpProductCommandPort {

    override fun save(product: BpProduct): BpProduct {
        val saved = productRepository.save(BpProductEntity.of(product))
        return saved.toDomain()
    }

    override fun getByProductIdOrThrow(productId: EntityId): BpProduct {
        return productRepository.findByProductId(productId)?.toDomain()
            ?: throw ApplicationException.ofBadRequest(ErrorCode.NOT_FOUND_PRODUCT)
    }
}
