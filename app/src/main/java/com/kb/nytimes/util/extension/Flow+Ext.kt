package com.kb.nytimes.util.extension

import android.util.Log
import com.kb.nytimes.foundation.exception.ApiException
import com.kb.nytimes.foundation.view.UIController
import com.kb.nytimes.util.Constants.ERROR_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resumeWithException

fun <T> Flow<T>.launchWith(
    controller: UIController,
    scope: CoroutineScope = controller,
    continuation: Continuation<Unit>? = null,
    onError: ((Throwable) -> Unit)? = null
) = this
    .catch {
        continuation?.resumeWithException(it)

        onError?.let { _ ->
            onError.invoke(it)
        } ?: run {
            Log.e(ERROR_TAG, it.localizedMessage)
        }
    }

.launchIn(scope)


fun <T> flowSingle(action: suspend () -> T): Flow<T> {
    return flow { emit(action.invoke()) }
}
