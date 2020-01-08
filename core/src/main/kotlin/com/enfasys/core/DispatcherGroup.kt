package com.enfasys.core

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherGroup {
    val IO: CoroutineDispatcher
    val Computation: CoroutineDispatcher
    val UI: CoroutineDispatcher
    val State: CoroutineDispatcher
}