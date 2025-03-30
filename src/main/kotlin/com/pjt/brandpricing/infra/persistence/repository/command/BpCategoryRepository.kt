package com.pjt.brandpricing.infra.persistence.repository.command

import com.pjt.brandpricing.infra.persistence.entity.BpCategoryEntity
import com.pjt.brandpricing.support.EntityId
import org.springframework.data.jpa.repository.JpaRepository

interface BpCategoryRepository : JpaRepository<BpCategoryEntity, Long> {
    fun existsByCategoryId(categoryId: EntityId): Boolean
}
