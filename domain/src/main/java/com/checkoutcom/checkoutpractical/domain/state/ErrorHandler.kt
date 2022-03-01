package com.checkoutcom.checkoutpractical.domain.state


interface ErrorHandler {
    fun getError(throwable: Throwable): ResultHandler.ErrorType
    fun getApiError(statusCode: Int, throwable: Throwable? = null): ResultHandler.ErrorType
}