package com.pjt.brandpricing.infra.persistence.repository.command

import com.pjt.brandpricing.infra.persistence.entity.BpSummaryUpdateFailLogEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BpSummaryUpdateFailLogRepository : JpaRepository<BpSummaryUpdateFailLogEntity, Long> {

}
