package com.kb.nytimes.foundation.view

import android.content.Context
import androidx.annotation.DrawableRes
import kotlinx.coroutines.CoroutineScope


interface UIController : CoroutineScope {

    fun showNoInternetMessage()
    val disposeBag: MutableList<() -> Unit>

    /**
     * Show a popup alert with [message].
     */
    fun showPopup(
        context: Context,
        title: String? = "Unknown Error!",
        message: String? = null,
        buttonLabel: String? = "Okay",
        @DrawableRes heroImage: Int? = null,
        onDismiss: (() -> Unit)? = null,
        errorCode: String? = null,
    )

    fun showNoConnectionPopup(
        context: Context
    )
}
