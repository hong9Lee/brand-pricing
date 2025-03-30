package com.pjt.brandpricing.adapter.controller.data.product

import java.math.BigDecimal

data class CreateProductRequest(
    val productName: String,
    val price: BigDecimal,
    val brandId: String,
    val categoryId: String
)
