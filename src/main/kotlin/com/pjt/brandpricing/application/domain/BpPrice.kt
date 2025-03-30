package com.pjt.brandpricing.application.domain

import java.math.BigDecimal

data class BpPrice(
    val value: BigDecimal = BigDecimal.ZERO,
) : Comparable<BpPrice> {
    constructor(value: Long) : this(
        value = BigDecimal(value)
    )

    fun isRemain(): Boolean {
        return value > BigDecimal.ZERO
    }

    operator fun plus(bpPrice: BpPrice): BpPrice {
        return BpPrice(this.value.plus(bpPrice.value))
    }

    operator fun minus(bpPrice: BpPrice): BpPrice {
        return BpPrice(this.value.minus(bpPrice.value))
    }

    operator fun times(times: Int): BpPrice {
        return BpPrice(this.value.times(BigDecimal(times)))
    }

    override fun compareTo(bpPrice: BpPrice): Int {
        return this.value.compareTo(bpPrice.value)
    }

    operator fun div(bpPrice: BpPrice): BpPrice {
        return BpPrice(this.value.div(bpPrice.value))
    }

    override fun toString(): String {
        return this.value.toString()
    }

    companion object {
        val ZERO = BpPrice()
    }

    fun isOver(bpPrice: BpPrice): Boolean {
        if (this.value < bpPrice.value) {
            return true;
        }
        return false;
    }

    fun isUnder(bpPrice: BpPrice): Boolean {
        if (this.value > bpPrice.value) {
            return true;
        }
        return false;
    }

    fun isEqual(bpPrice: BpPrice): Boolean {
        return bpPrice.value == this.value
    }
}
