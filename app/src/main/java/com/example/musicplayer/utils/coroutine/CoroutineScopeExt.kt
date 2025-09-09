package com.example.musicplayer.utils.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.createChildScope(
    block: (Job) -> CoroutineContext
): CoroutineScope = this + block(this.coroutineContext.job)