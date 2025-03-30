package com.pjt.brandpricing.adapter.controller.data.product

import java.math.BigDecimal

data class UpdateProductRequest(
    val productId: String,
    val productName: String? = null,
    val price: BigDecimal? = null,
    val productStatus: String? = null
)
