package com.pjt.brandpricing.infra.persistence.entity

import com.pjt.brandpricing.application.domain.BpProductMapping
import com.pjt.brandpricing.infra.persistence.entity.base.BaseTimeEntity
import com.pjt.brandpricing.support.EntityId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "bp_product_mapping")
class BpProductMappingEntity(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "brand_id"))
    val brandId: EntityId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "category_id"))
    val categoryId: EntityId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "product_id"))
    val productId: EntityId,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    ) : BaseTimeEntity() {

    fun toDomain(): BpProductMapping = BpProductMapping(
        seq = seq,
        brandId = brandId,
        categoryId = categoryId,
        productId = productId,
        isActive = isActive
    )

    companion object {
        fun of(bpProductMapping: BpProductMapping): BpProductMappingEntity = BpProductMappingEntity(
            brandId = bpProductMapping.brandId,
            categoryId = bpProductMapping.categoryId,
            productId = bpProductMapping.productId,
            isActive = bpProductMapping.isActive
        ).apply { this.seq = bpProductMapping.seq }
    }
}

