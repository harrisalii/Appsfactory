package com.appsfactory.test.utils

import android.util.Log
import com.appsfactory.test.utils.extensions.ifNull

inline fun <reified T> logDebug(msg: String?) {
    Log.d(T::class.java.simpleName, msg.ifNull { "null" })
}

fun Any.log(msg: String?) {
    this::class.java
    Log.d(this::class.java.simpleName, msg.ifNull { "null" })
}

inline fun <reified T> logError(msg: String?) {
    Log.e(T::class.java.simpleName, msg.ifNull { "null" })
}

inline fun <reified T> logInfo(msg: String?) {
    Log.i(T::class.java.simpleName, msg.ifNull { "null" })
}

fun emptyString() = String()

fun randomColor() = (Math.random() * 16777215).toInt() or (0xFF shl 24)