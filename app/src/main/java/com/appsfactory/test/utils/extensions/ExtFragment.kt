package com.appsfactory.test.utils.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(msg = msg, length = length)
}

fun Fragment.progressDialog() = requireContext().progressDialog()