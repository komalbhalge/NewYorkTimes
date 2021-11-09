package com.kb.nytimes.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kb.nytimes.R
import com.kb.nytimes.foundation.view.UIController
import com.kb.nytimes.util.Constants.ERROR_TAG
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), UIController {

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    val shouldShowNoInternetAlert = MutableLiveData<Boolean>()
    val shouldShowApiErrorAlert = MutableLiveData<Throwable>()

    override fun showNoInternetMessage() {
        shouldShowNoInternetAlert.value = true
    }

    override val disposeBag: MutableList<() -> Unit>
        get() = mutableListOf()

    override fun showPopup(
        context: Context,
        title: String?,
        message: String?,
        buttonLabel: String?,
        heroImage: Int?,
        onDismiss: (() -> Unit)?,
        errorCode: String?
    ) {
        AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                R.string.okay
            ) { dialogInterface, i -> (context as Activity).finish() }.show()
    }

    override fun showNoConnectionPopup(context: Context) {
        AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.no_connection_title)
            .setMessage(R.string.no_connection_message)
            .setPositiveButton(
                R.string.no_connection_retry
            ) { dialogInterface, i -> (context as Activity).finish() }.show()
    }
}