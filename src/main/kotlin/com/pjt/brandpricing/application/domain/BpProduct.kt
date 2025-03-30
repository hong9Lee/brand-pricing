package com.pjt.brandpricing.application.domain

import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.support.EntityId

class BpProduct(
    val seq: Long? = null,
    val productId: EntityId,
    var productStatus: ProductStatus,
    var productName: String,
    var price: BpPrice,
) {

    companion object {
        fun create(
            productName: String,
            price: BpPrice,
        ): BpProduct {
            return BpProduct(
                productId = EntityId(),
                productName = productName,
                price = price,
                productStatus = ProductStatus.DISPLAY
            )
        }
    }

    private fun update(
        productName: String?,
        price: BpPrice?,
        productStatus: ProductStatus?,
    ) {
        productName?.let { this.productName = it }
        price?.let { this.price = it }
        productStatus?.let { this.productStatus = it }
    }

    fun validateUpdatable(name: String?, price: BpPrice?, status: ProductStatus?) {
        if (!needUpdate(name, price, status)) {
            throw ApplicationException.ofBadRequest(ErrorCode.INVALID_UPDATE_REQUEST)
        }
    }

    fun applyUpdate(name: String?, price: BpPrice?, status: ProductStatus?): Boolean {
        val isSummaryUpdate = needsSummaryUpdate(price, status)
        update(name, price, status)
        return isSummaryUpdate
    }

    private fun needUpdate(productName: String?, price: BpPrice?, productStatus: ProductStatus?): Boolean {
        val isNameChanged = productName != null && this.productName != productName
        val isPriceChanged = price != null && !this.price.isEqual(price)
        val isStatusChanged = productStatus != null && this.productStatus != productStatus

        return isNameChanged || isPriceChanged || isStatusChanged
    }

    private fun needsSummaryUpdate(newPrice: BpPrice?, newStatus: ProductStatus?): Boolean {
        val isPriceChanged = newPrice != null && !this.price.isEqual(newPrice)
        val isDeleted = newStatus == ProductStatus.DELETED && this.productStatus != ProductStatus.DELETED
        val isDisplayChanged = newStatus == ProductStatus.DISPLAY && this.productStatus != ProductStatus.DISPLAY

        return isPriceChanged || isDeleted || isDisplayChanged
    }

    fun isDisplayOff(newStatus: ProductStatus?): Boolean {
        return newStatus != null &&
                this.productStatus == ProductStatus.DISPLAY &&
                newStatus != ProductStatus.DISPLAY
    }

    fun isDisplayOn(newStatus: ProductStatus?): Boolean {
        return newStatus != null &&
                this.productStatus != ProductStatus.DISPLAY &&
                newStatus == ProductStatus.DISPLAY
    }

}
