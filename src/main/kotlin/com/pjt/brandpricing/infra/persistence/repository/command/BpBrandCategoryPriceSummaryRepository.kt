package com.pjt.brandpricing.infra.persistence.repository.command

import com.pjt.brandpricing.infra.persistence.entity.BpBrandCategoryPriceSummaryEntity
import com.pjt.brandpricing.support.EntityId
import org.springframework.data.jpa.repository.JpaRepository

interface BpBrandCategoryPriceSummaryRepository : JpaRepository<BpBrandCategoryPriceSummaryEntity, Long> {
    fun findByBrandIdAndCategoryId(brandId: EntityId, categoryId: EntityId): BpBrandCategoryPriceSummaryEntity?
}
