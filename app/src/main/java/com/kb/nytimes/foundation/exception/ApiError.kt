package com.kb.nytimes.foundation.exception

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName(value = "code", alternate = ["errorCode"]) val code: String? = null,

    @SerializedName("title", alternate = ["errorTitle"]) val title: String? = null,

    @SerializedName("message", alternate = ["errorMessage"]) val message: String? = null,

    @SerializedName("description", alternate = ["errorDesc"]) val description: String? = null

)
