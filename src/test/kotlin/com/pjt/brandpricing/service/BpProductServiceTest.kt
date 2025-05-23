package com.pjt.brandpricing.service

import com.pjt.brandpricing.application.domain.BpPrice
import com.pjt.brandpricing.application.domain.BpProduct
import com.pjt.brandpricing.application.domain.BpProductMapping
import com.pjt.brandpricing.application.domain.data.`in`.CreateProductCommand
import com.pjt.brandpricing.application.domain.data.`in`.UpdateProductCommand
import com.pjt.brandpricing.application.domain.enums.ProductStatus
import com.pjt.brandpricing.application.service.command.BpProductService
import com.pjt.brandpricing.fake.FakeBpBrandCommandPort
import com.pjt.brandpricing.fake.FakeBpCategoryCommandPort
import com.pjt.brandpricing.fake.FakeBpProductCommandPort
import com.pjt.brandpricing.fake.FakeBpProductMappingPort
import com.pjt.brandpricing.fake.StubApplicationEventPublisher
import com.pjt.brandpricing.support.EntityId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Description

class BpProductServiceTest {
    private val fakeBpBrandCommandPort = FakeBpBrandCommandPort()
    private val fakeBpCategoryCommandPort = FakeBpCategoryCommandPort()
    private val fakeProductCommandPort = FakeBpProductCommandPort()
    private val fakeProductMappingPort = FakeBpProductMappingPort()
    private val eventPublisher = StubApplicationEventPublisher()
    private val bpProductService = BpProductService(
        fakeBpBrandCommandPort,
        fakeBpCategoryCommandPort,
        fakeProductCommandPort,
        fakeProductMappingPort,
        eventPublisher
    )
    private lateinit var brandId: EntityId
    private lateinit var categoryId: EntityId

    companion object {
        private const val PRODUCT_NAME = "testProductName"
        private const val PRODUCT_PRICE = 12000L
    }

    @BeforeEach
    fun setUp() {
        brandId = EntityId()
        categoryId = EntityId()
        fakeBpBrandCommandPort.save(brandId)
        fakeBpCategoryCommandPort.save(categoryId)
    }

    @Test
    @Description("상품을 생성할 수 있다.")
    fun createProduct() {
        // given
        val command = createProductCommand(PRODUCT_NAME, PRODUCT_PRICE)

        // when
        val result = bpProductService.create(command)

        // then
        assertThat(result.productName).isEqualTo(command.productName)
        assertThat(result.price).isEqualTo(command.price)
    }

    @Test
    @Description("상품 정보를 변경할 수 있다.")
    fun updateProduct() {
        // given
        val updatePriceName = "updatePriceName"
        val updatePricePrice = BpPrice(17000)
        val product = createAndSaveProduct(PRODUCT_NAME, PRODUCT_PRICE)
        val updateCommand = UpdateProductCommand(
            productId = product.productId,
            productName = updatePriceName,
            price = updatePricePrice,
            productStatus = ProductStatus.DISPLAY,
        )

        // when
        bpProductService.update(updateCommand)

        // then
        val updated = fakeProductCommandPort.getByProductIdOrThrow(product.productId)
        assertThat(updated.productName).isEqualTo(updatePriceName)
        assertThat(updated.price.value).isEqualTo(updatePricePrice.value)
        assertThat(updated.productStatus).isEqualTo(ProductStatus.DISPLAY)
    }

    @Test
    @Description("상품을 삭제할 수 있다.")
    fun deleteProduct() {
        // given
        val product = createAndSaveProduct(PRODUCT_NAME, PRODUCT_PRICE)
        val deleteCommand = UpdateProductCommand(
            productId = product.productId,
            productName = product.productName,
            price = product.price,
            productStatus = ProductStatus.DELETED,
        )

        // when
        bpProductService.update(deleteCommand)

        // then
        val deleted = fakeProductCommandPort.getByProductIdOrThrow(product.productId)
        assertThat(deleted.productStatus).isEqualTo(ProductStatus.DELETED)
    }

    @Test
    @Description("상품을 삭제하면 매핑 정보도 비활성화 할 수 있다.")
    fun deleteProductAndDeactivateMapping() {
        // given
        val product = createAndSaveProduct(PRODUCT_NAME, PRODUCT_PRICE)

        val deleteCommand = UpdateProductCommand(
            productId = product.productId,
            productName = product.productName,
            price = product.price,
            productStatus = ProductStatus.DELETED,
        )

        // when
        bpProductService.update(deleteCommand)

        // then
        val mapping = fakeProductMappingPort.findByProductId(product.productId)
        assertThat(mapping.isActive).isFalse()
    }


    private fun createProductCommand(name: String, price: Long) = CreateProductCommand(
        productName = name,
        price = BpPrice(price),
        brandId = brandId,
        categoryId = categoryId
    )

    private fun createAndSaveProduct(name: String, price: Long): BpProduct {
        val product = BpProduct.create(name, BpPrice(price))
        fakeProductCommandPort.save(product)

        val mapping = BpProductMapping(
            productId = product.productId,
            brandId = brandId,
            categoryId = categoryId,
            isActive = true
        )
        fakeProductMappingPort.save(mapping)
        return product
    }
}
