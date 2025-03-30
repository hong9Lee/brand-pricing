package com.pjt.brandpricing.infra.persistence.repository.command

import com.pjt.brandpricing.infra.persistence.entity.BpProductEntity
import com.pjt.brandpricing.support.EntityId
import org.springframework.data.jpa.repository.JpaRepository

interface BpProductRepository : JpaRepository<BpProductEntity, Long> {
    fun findByProductId(productId: EntityId): BpProductEntity?
}
