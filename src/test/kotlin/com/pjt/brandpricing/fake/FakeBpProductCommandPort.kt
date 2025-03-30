package com.pjt.brandpricing.fake

import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.application.domain.data.port.command.BpProductCommandPort
import com.pjt.brandpricing.support.EntityId

class FakeBpProductCommandPort : BpProductCommandPort {
    val savedProducts = mutableListOf<BpProduct>()

    override fun save(product: BpProduct): BpProduct {
        savedProducts.removeIf { it.productId == product.productId }
        savedProducts.add(product)
        return product
    }

    override fun getByProductIdOrThrow(productId: EntityId): BpProduct {
        return savedProducts.firstOrNull { it.productId == productId }
            ?: throw IllegalArgumentException("Product not found")
    }
}

