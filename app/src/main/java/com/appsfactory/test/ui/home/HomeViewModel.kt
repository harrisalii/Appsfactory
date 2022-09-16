package com.appsfactory.test.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsfactory.test.domain.repository.LastFMRepository
import com.appsfactory.test.domain.util.Result
import com.appsfactory.test.utils.logDebug
import com.appsfactory.test.utils.logError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: LastFMRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.getTopAlbumsOfArtist(name = "cher").collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        logDebug<HomeViewModel>(result.data.toString())
                        logDebug<HomeViewModel>("Success!")
                    }
                    is Result.Error -> logError<HomeViewModel>(result.error)
                }
            }
        }
    }
}