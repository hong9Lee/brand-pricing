package com.pjt.brandpricing.support

import com.fasterxml.jackson.core.type.TypeReference

class ListStringConverter : GenericJsonConverter<List<String>>(
    object : TypeReference<List<String>>() {}
)
