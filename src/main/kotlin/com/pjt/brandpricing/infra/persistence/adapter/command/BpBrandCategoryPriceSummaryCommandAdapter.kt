package com.pjt.brandpricing.infra.persistence.adapter.command

import com.pjt.brandpricing.application.domain.BpBrandCategoryPriceSummary
import com.pjt.brandpricing.application.domain.data.port.command.BpBrandCategoryPriceSummaryCommandPort
import com.pjt.brandpricing.infra.persistence.entity.BpBrandCategoryPriceSummaryEntity
import com.pjt.brandpricing.infra.persistence.repository.command.BpBrandCategoryPriceSummaryRepository
import com.pjt.brandpricing.support.EntityId
import org.springframework.stereotype.Repository

@Repository
class BpBrandCategoryPriceSummaryCommandAdapter(
    private val bpBrandCategoryPriceSummaryRepository: BpBrandCategoryPriceSummaryRepository,
) : BpBrandCategoryPriceSummaryCommandPort {
    override fun save(brandCategoryPriceSummary: BpBrandCategoryPriceSummary): BpBrandCategoryPriceSummary {
        return bpBrandCategoryPriceSummaryRepository.save(BpBrandCategoryPriceSummaryEntity.of(brandCategoryPriceSummary))
            .toDomain()
    }

    override fun findByBrandIdAndCategoryId(brandId: EntityId, categoryId: EntityId): BpBrandCategoryPriceSummary? {
        return bpBrandCategoryPriceSummaryRepository.findByBrandIdAndCategoryId(brandId, categoryId)?.toDomain()
    }

}
