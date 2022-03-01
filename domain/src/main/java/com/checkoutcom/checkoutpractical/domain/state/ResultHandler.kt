package com.checkoutcom.checkoutpractical.domain.state

sealed class ResultHandler<out T>(
    val data: T? = null,
    val error: ErrorType? = null,
) {
    class Success<T>(data: T) : ResultHandler<T>(data)
    class Loading<T>(data: T? = null) : ResultHandler<T>(data)
    class Error<T>(error: ErrorType? = null, data: T? = null) : ResultHandler<T>(data, error)

    sealed class ErrorType(
        val throwable: Throwable? = null,
        val message: Int? = null,
    ) {
        class IOError(throwable: Throwable? = null) : ErrorType(throwable)
        class HttpError(throwable: Throwable? = null, val statusCode: Int) : ErrorType(throwable)
        class Unknown(throwable: Throwable? = null) : ErrorType(throwable)
    }
}