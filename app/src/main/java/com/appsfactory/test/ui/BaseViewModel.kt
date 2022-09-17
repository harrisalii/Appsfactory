package com.appsfactory.test.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {

    private val mLoading = MutableStateFlow(false)
    val isLoading = mLoading.asStateFlow()

    protected fun showLoader() {
        mLoading.value = true
    }

    protected fun hideLoader() {
        mLoading.value = false
    }
}