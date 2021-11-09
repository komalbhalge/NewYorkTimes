package com.kb.nytimes.testutils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.stubbing.OngoingStubbing

fun <T> OngoingStubbing<Flow<T>>.thenReturnFlow(
    vararg items: T
): OngoingStubbing<Flow<T>> {
    val obs = items
        .map { flowOf(it) }
        .drop(1)
        .toTypedArray()
    return this.thenReturn(flowOf(items.first()), *obs)
}