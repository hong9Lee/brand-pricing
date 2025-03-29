package com.pjt.brandpricing.support

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class ZonedDateTimeUtils {
    companion object {
        private val SEOUL_ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")

        fun nowOfSeoul(): ZonedDateTime {
            return ofSeoul(LocalDateTime.now())
        }

        fun ofSeoul(localDateTime: LocalDateTime): ZonedDateTime {
            return ZonedDateTime.of(localDateTime, SEOUL_ZONE_ID)
        }
    }
}
