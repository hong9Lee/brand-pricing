package com.pjt.brandpricing.infra.persistence.repository.command

import com.pjt.brandpricing.infra.persistence.entity.BpProductMappingEntity
import com.pjt.brandpricing.support.EntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface BpProductMappingRepository : JpaRepository<BpProductMappingEntity, Long> {
    fun findByProductId(productId: EntityId): BpProductMappingEntity?

    @Modifying
    @Query("UPDATE BpProductMappingEntity m SET m.isActive = :isActive WHERE m.productId = :productId")
    fun updateIsActiveByProductId(productId: EntityId, isActive: Boolean): Int
}
