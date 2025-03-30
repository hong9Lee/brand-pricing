package com.pjt.brandpricing.infra.persistence.repository.command

import com.pjt.brandpricing.infra.persistence.entity.BpBrandEntity
import com.pjt.brandpricing.support.EntityId
import org.springframework.data.jpa.repository.JpaRepository

interface BpBrandRepository : JpaRepository<BpBrandEntity, Long> {
    fun findByBrandId(brandId: EntityId): BpBrandEntity?
    fun existsByBrandId(brandId: EntityId): Boolean
}
