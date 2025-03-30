package com.pjt.brandpricing.infra.persistence.repository.command

import com.pjt.brandpricing.infra.persistence.entity.BpProductMappingEntity
import com.pjt.brandpricing.support.EntityId
import org.springframework.data.jpa.repository.JpaRepository

interface BpProductMappingRepository : JpaRepository<BpProductMappingEntity, Long> {
    fun findByProductId(productId: EntityId): BpProductMappingEntity?
}
