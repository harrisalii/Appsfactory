package com.appsfactory.test.data.remote.repository

import com.appsfactory.test.domain.util.Result
import com.appsfactory.test.utils.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    suspend inline fun <reified T : Any, reified V : Any> makeRequest(
        crossinline request: suspend () -> Response<T>,
        crossinline response: suspend T.() -> V
    ): Flow<Result<V>> = flow {
        try {
            val result = request.invoke()
            if (result.isSuccessful) {
                val dto = result.body()!!
                emit(Result.Success(dto.response()))
            } else {
                emit(Result.Error("Request failed. Please try again later."))
            }
        } catch (e: IOException) {
            emit(Result.Error("Please check your internet connection!"))
        } catch (e: HttpException) {
            emit(Result.Error("Request failed. Please try again later."))
        }
    }.catch { e ->
        logError<BaseRepository>("Flow Error -> ${e.message}")
        emit(Result.Error("Unknown error occurred."))
    }
}