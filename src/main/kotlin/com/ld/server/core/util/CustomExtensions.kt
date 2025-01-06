package com.ld.server.core.util

import com.ld.server.api.dto.IdResponse

fun Long.toResponse() = IdResponse(this)
