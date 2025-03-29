package com.pjt.brandpricing.infra.persistence.entity

import com.pjt.brandpricing.application.domain.BpBrand
import com.pjt.brandpricing.application.domain.enums.BrandStatus
import com.pjt.brandpricing.infra.persistence.entity.base.BaseTimeEntity
import com.pjt.brandpricing.support.EntityId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "bp_brand")
class BpBrandEntity(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "brand_id"))
    val brandId: EntityId,

    @Column(name = "brand_name", nullable = false)
    val brandName: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "brand_status", nullable = false)
    val brandStatus: BrandStatus,

    ) : BaseTimeEntity() {

    fun toDomain(): BpBrand = BpBrand(
        seq = seq,
        brandId = brandId,
        brandStatus = brandStatus,
        brandName = brandName
    )

    companion object {
        fun of(bpBrand: BpBrand): BpBrandEntity {
            return BpBrandEntity(
                brandId = bpBrand.brandId,
                brandName = bpBrand.brandName,
                brandStatus = bpBrand.brandStatus
            ).apply {
                this.seq = bpBrand.seq
            }
        }
    }
}

