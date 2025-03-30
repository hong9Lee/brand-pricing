package com.pjt.brandpricing.service

import com.pjt.brandpricing.application.domain.BpBrand
import com.pjt.brandpricing.application.domain.data.`in`.UpdateBrandCommand
import com.pjt.brandpricing.application.domain.enums.BrandStatus
import com.pjt.brandpricing.application.service.command.BpBrandService
import com.pjt.brandpricing.fake.FakeBpBrandCommandPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Description

class BpBrandServiceTest {
    private val fakeBrandCommandPort = FakeBpBrandCommandPort()
    private val bpBrandService = BpBrandService(fakeBrandCommandPort)

    companion object {
        private const val BRAND_NAME = "testBrandName"
    }

    @Test
    @Description("브랜드를 생성할 수 있다.")
    fun createBrand() {
        // given
        // when
        val result = bpBrandService.create(BRAND_NAME)

        // then
        assertThat(result.brandName).isEqualTo(BRAND_NAME)
        assertThat(result.brandStatus).isEqualTo(BrandStatus.ACTIVE)
    }

    @Test
    @Description("브랜드 정보를 변경할 수 있다.")
    fun updateBrand() {
        // given
        val updateName = "updateBrand"
        val brand = createAndSaveBrand(BRAND_NAME)
        val updateCommand = UpdateBrandCommand(
            brandId = brand.brandId,
            brandName = updateName,
            brandStatus = BrandStatus.INACTIVE,
        )

        // when
        bpBrandService.update(updateCommand)

        // then
        val updated = fakeBrandCommandPort.findByBrandIdOrThrow(brand.brandId)
        assertThat(updated.brandName).isEqualTo(updateName)
        assertThat(updated.brandStatus).isEqualTo(BrandStatus.INACTIVE)
    }

    @Test
    @Description("브랜드를 삭제할 수 있다.")
    fun deleteBrand() {
        // given
        val brand = createAndSaveBrand(BRAND_NAME)
        val deleteCommand = UpdateBrandCommand(
            brandId = brand.brandId,
            brandName = brand.brandName,
            brandStatus = BrandStatus.DELETED,
        )

        // when
        bpBrandService.update(deleteCommand)

        // then
        val deleted = fakeBrandCommandPort.findByBrandIdOrThrow(brand.brandId)
        assertThat(deleted.brandStatus).isEqualTo(BrandStatus.DELETED)
    }

    private fun createAndSaveBrand(name: String): BpBrand {
        return fakeBrandCommandPort.save(BpBrand.create(name))
    }
}

