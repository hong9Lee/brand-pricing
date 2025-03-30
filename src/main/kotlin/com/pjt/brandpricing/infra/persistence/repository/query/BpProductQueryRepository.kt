package com.pjt.brandpricing.infra.persistence.repository.query

import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.infra.persistence.entity.BpProductEntity
import com.pjt.brandpricing.infra.persistence.entity.QBpProductEntity.bpProductEntity
import com.pjt.brandpricing.infra.persistence.entity.QBpProductMappingEntity.bpProductMappingEntity
import com.pjt.brandpricing.support.EntityId
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BpProductQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {

    fun findAllByBrandIdAndCategoryId(
        brandId: EntityId,
        categoryId: EntityId,
        isActive: Boolean,
        productStatus: ProductStatus,
    ): List<BpProductEntity> {
        return jpaQueryFactory
            .select(bpProductEntity)
            .from(bpProductEntity)
            .join(bpProductMappingEntity).on(
                bpProductEntity.productId.eq(bpProductMappingEntity.productId)
            )
            .where(
                bpProductMappingEntity.brandId.eq(brandId),
                bpProductMappingEntity.categoryId.eq(categoryId),
                bpProductMappingEntity.isActive.eq(isActive),
                bpProductEntity.productStatus.eq(productStatus),
            )
            .fetch()
    }
}
