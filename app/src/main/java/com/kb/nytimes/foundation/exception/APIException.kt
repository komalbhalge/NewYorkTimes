package com.kb.nytimes.foundation.exception

import java.io.IOException

class ApiException(
    val error: ApiError? = null
) : IOException("There was a problem processing the request.") {

    override val message: String?
        get() = error?.message ?: super.message
}