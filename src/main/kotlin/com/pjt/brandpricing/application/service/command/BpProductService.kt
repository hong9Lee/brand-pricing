package com.pjt.brandpricing.application.service.command

import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.application.domain.BpProductMapping
import com.pjt.brandpricing.application.domain.data.`in`.CreateProductCommand
import com.pjt.brandpricing.application.domain.data.`in`.UpdateProductCommand
import com.pjt.brandpricing.application.domain.data.out.CreateProductResult
import com.pjt.brandpricing.application.domain.data.out.UpdateProductResult
import com.pjt.brandpricing.application.domain.data.port.command.BpProductCommandPort
import com.pjt.brandpricing.application.domain.data.port.command.BpProductMappingPort
import com.pjt.brandpricing.application.domain.event.BpProductSummaryEvent
import com.pjt.brandpricing.support.EntityId
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BpProductService(
    private val bpProductCommandPort: BpProductCommandPort,
    private val bpProductMappingPort: BpProductMappingPort,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun create(command: CreateProductCommand): CreateProductResult {
        val bpProduct = createProduct(command)
        createProductMapping(command.brandId, command.categoryId, bpProduct.productId)

        publishProductCreateSummaryEvent(bpProduct)
        return CreateProductResult.of(bpProduct)
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

        product.update(
            productName = command.productName,
            price = command.price,
            productStatus = command.productStatus
        )

        val savedBpProduct = bpProductCommandPort.save(product)
        publishProductCreateSummaryEvent(savedBpProduct)
        return UpdateProductResult.of(savedBpProduct)
    }

    private fun publishProductCreateSummaryEvent(bpProduct: BpProduct) {
        eventPublisher.publishEvent(
            BpProductSummaryEvent(productId = bpProduct.productId)
        )
    }
}

