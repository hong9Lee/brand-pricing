package com.pjt.brandpricing.application.domain

class BpProducts(
    private val products: List<BpProduct>,
) {
    fun findMinPriceProducts(): List<BpProduct> {
        val minPrice = products.minOfOrNull { it.price } ?: return emptyList()
        return products.filter { it.price.isEqual(minPrice) }
    }

    fun findMaxPriceProducts(): List<BpProduct> {
        val maxPrice = products.maxOfOrNull { it.price } ?: return emptyList()
        return products.filter { it.price.isEqual(maxPrice) }
    }

    fun isEmpty(): Boolean {
        return products.isEmpty()
    }
}

