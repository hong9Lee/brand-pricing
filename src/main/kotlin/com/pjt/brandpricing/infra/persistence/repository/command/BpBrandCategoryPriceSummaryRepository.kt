package com.pjt.brandpricing.infra.persistence.repository.command

import com.pjt.brandpricing.infra.persistence.entity.BpBrandCategoryPriceSummaryEntity
import com.pjt.brandpricing.support.EntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface BpBrandCategoryPriceSummaryRepository : JpaRepository<BpBrandCategoryPriceSummaryEntity, Long> {
    fun findByBrandIdAndCategoryId(brandId: EntityId, categoryId: EntityId): BpBrandCategoryPriceSummaryEntity?

    @Modifying
    @Query("DELETE FROM BpBrandCategoryPriceSummaryEntity s WHERE s.brandId = :brandId AND s.categoryId = :categoryId")
    fun deleteByBrandIdAndCategoryId(brandId: EntityId, categoryId: EntityId): Int
}
