package com.pjt.brandpricing.application.service.command

import com.pjt.brandpricing.application.domain.BpBrand
import com.pjt.brandpricing.application.domain.data.`in`.UpdateBrandCommand
import com.pjt.brandpricing.application.domain.data.out.CreateBrandResult
import com.pjt.brandpricing.application.domain.data.out.UpdateBrandResult
import com.pjt.brandpricing.application.domain.data.port.command.BpBrandCommandPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BpBrandService(
    private val bpBrandCommandPort: BpBrandCommandPort,
) {

    /** 브랜드를 생성한다. */
    fun create(brandName: String): CreateBrandResult {
        return CreateBrandResult.of(bpBrandCommandPort.save(BpBrand.create(brandName)))
    }

    /** 브랜드를 업데이트 한다. */
    fun update(updateBrandCommand: UpdateBrandCommand): UpdateBrandResult {
        val bpBrand = bpBrandCommandPort.findByBrandIdOrThrow(updateBrandCommand.brandId)

        bpBrand.update(
            brandStatus = updateBrandCommand.brandStatus,
            brandName = updateBrandCommand.brandName,
        )

        val savedBpBrand = bpBrandCommandPort.save(bpBrand)
        return UpdateBrandResult.of(
            bpBrand = savedBpBrand
        )
    }
}

