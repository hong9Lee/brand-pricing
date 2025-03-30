package com.pjt.brandpricing.infra.persistence.entity

import com.pjt.brandpricing.application.domain.BpBrandCategoryPriceSummary
import com.pjt.brandpricing.application.domain.BpPrice
import com.pjt.brandpricing.infra.persistence.entity.base.BaseTimeEntity
import com.pjt.brandpricing.support.EntityId
import com.pjt.brandpricing.support.ListStringConverter
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "bp_brand_category_price_summary")
class BpBrandCategoryPriceSummaryEntity(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "brand_id"))
    val brandId: EntityId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "category_id"))
    val categoryId: EntityId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "min_price"))
    var minPrice: BpPrice,

    @Column(name = "min_price_product_ids")
    @Convert(converter = ListStringConverter::class)
    var minPriceProductIds: List<String>,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "max_price"))
    var maxPrice: BpPrice,

    @Column(name = "max_price_product_ids")
    @Convert(converter = ListStringConverter::class)
    var maxPriceProductIds: List<String>,

    ) : BaseTimeEntity() {

    fun toDomain(): BpBrandCategoryPriceSummary = BpBrandCategoryPriceSummary(
        seq = seq,
        brandId = brandId,
        categoryId = categoryId,
        minPrice = minPrice,
        minPriceProductIds = minPriceProductIds.map { EntityId(it) },
        maxPrice = maxPrice,
        maxPriceProductIds = maxPriceProductIds.map { EntityId(it) }
    )

    companion object {
        fun of(bpBrandCategoryPriceSummary: BpBrandCategoryPriceSummary): BpBrandCategoryPriceSummaryEntity {
            return BpBrandCategoryPriceSummaryEntity(
                brandId = bpBrandCategoryPriceSummary.brandId,
                categoryId = bpBrandCategoryPriceSummary.categoryId,
                minPrice = bpBrandCategoryPriceSummary.minPrice,
                minPriceProductIds = bpBrandCategoryPriceSummary.minPriceProductIds.map { it.value },
                maxPrice = bpBrandCategoryPriceSummary.maxPrice,
                maxPriceProductIds = bpBrandCategoryPriceSummary.maxPriceProductIds.map { it.value },
            ).apply {
                this.seq = bpBrandCategoryPriceSummary.seq
            }
        }
    }
}

