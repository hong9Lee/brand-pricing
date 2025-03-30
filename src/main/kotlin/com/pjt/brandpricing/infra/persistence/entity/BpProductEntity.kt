package com.pjt.brandpricing.infra.persistence.entity

import com.pjt.brandpricing.application.domain.BpPrice
import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.application.domain.enums.ProductStatus
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
@Table(name = "bp_product")
class BpProductEntity(

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "product_id"))
    val productId: EntityId,

    @Column(name = "product_status")
    @Enumerated(EnumType.STRING)
    val productStatus: ProductStatus,

    @Column(name = "product_name")
    val productName: String,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "price"))
    var price: BpPrice,

    ) : BaseTimeEntity() {

    fun toDomain(): BpProduct = BpProduct(
        seq = seq,
        productId = productId,
        productStatus = productStatus,
        productName = productName,
        price = price
    )

    companion object {
        fun of(bpProduct: BpProduct): BpProductEntity = BpProductEntity(
            productId = bpProduct.productId,
            productStatus = bpProduct.productStatus,
            productName = bpProduct.productName,
            price = bpProduct.price
        ).apply {
            seq = bpProduct.seq
        }
    }
}
