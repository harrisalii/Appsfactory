package com.appsfactory.test.utils

import android.util.Log
import com.appsfactory.test.utils.extensions.ifNull

inline fun <reified T> logDebug(msg: String?) {
    Log.d(T::class.java.simpleName, msg.ifNull { "null" })
}

inline fun <reified T> logError(msg: String?) {
    Log.e(T::class.java.simpleName, msg.ifNull { "null" })
}