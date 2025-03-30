package com.pjt.brandpricing.application.service.command

import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.application.domain.BpProductMapping
import com.pjt.brandpricing.application.domain.data.`in`.CreateProductCommand
import com.pjt.brandpricing.application.domain.data.`in`.UpdateProductCommand
import com.pjt.brandpricing.application.domain.data.out.CreateProductResult
import com.pjt.brandpricing.application.domain.data.out.UpdateProductResult
import com.pjt.brandpricing.application.domain.data.port.command.BpBrandCommandPort
import com.pjt.brandpricing.application.domain.data.port.command.BpCategoryCommandPort
import com.pjt.brandpricing.application.domain.data.port.command.BpProductCommandPort
import com.pjt.brandpricing.application.domain.data.port.command.BpProductMappingPort
import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.application.domain.event.BpProductSummaryEvent
import com.pjt.brandpricing.application.domain.exception.ApplicationException
import com.pjt.brandpricing.application.domain.exception.ErrorCode
import com.pjt.brandpricing.support.EntityId
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BpProductService(
    private val bpBrandCommandPort: BpBrandCommandPort,
    private val bpCategoryCommandPort: BpCategoryCommandPort,
    private val bpProductCommandPort: BpProductCommandPort,
    private val bpProductMappingPort: BpProductMappingPort,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun create(command: CreateProductCommand): CreateProductResult {
        validateBrandAndCategoryExist(command.brandId, command.categoryId)

        val bpProduct = createProduct(command)
        createProductMapping(command.brandId, command.categoryId, bpProduct.productId)

        publishProductCreateSummaryEvent(bpProduct)
        return CreateProductResult.of(bpProduct)
    }

    private fun validateBrandAndCategoryExist(brandId: EntityId, categoryId: EntityId) {
        if (!bpBrandCommandPort.existsByBrandId(brandId)) {
            throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_BRAND)
        }
        if (!bpCategoryCommandPort.existsByCategoryId(categoryId)) {
            throw ApplicationException.ofNotFound(ErrorCode.NOT_FOUND_CATEGORY)
        }
    }

    private fun createProduct(command: CreateProductCommand) =
        bpProductCommandPort.save(
            BpProduct.create(
                productName = command.productName,
                price = command.price
            )
        )

    private fun createProductMapping(
        brandId: EntityId,
        categoryId: EntityId,
        productId: EntityId,
    ) {
        bpProductMappingPort.save(
            BpProductMapping(
                brandId = brandId,
                categoryId = categoryId,
                productId = productId
            )
        )
    }

    @Transactional
    fun update(command: UpdateProductCommand): UpdateProductResult {
        val product = bpProductCommandPort.getByProductIdOrThrow(command.productId)

        /** 상품 업데이트 가능 여부 검사 */
        product.validateUpdatable(command.productName, command.price, command.productStatus)
        handleProductMappingStatus(product, command.productStatus)
        val isSummaryUpdate = product.applyUpdate(command.productName, command.price, command.productStatus)

        val savedProduct = bpProductCommandPort.save(product)
        if (isSummaryUpdate) {
            publishProductCreateSummaryEvent(savedProduct)
        }
        return UpdateProductResult.of(savedProduct)
    }

    private fun handleProductMappingStatus(product: BpProduct, newStatus: ProductStatus?) {
        if (newStatus == null) return

        when {
            product.isDisplayOff(newStatus) -> bpProductMappingPort.updateActiveByProductId(product.productId, false)
            product.isDisplayOn(newStatus) -> bpProductMappingPort.updateActiveByProductId(product.productId, true)
        }
    }

    private fun publishProductCreateSummaryEvent(bpProduct: BpProduct) {
        eventPublisher.publishEvent(
            BpProductSummaryEvent(productId = bpProduct.productId)
        )
    }
}

