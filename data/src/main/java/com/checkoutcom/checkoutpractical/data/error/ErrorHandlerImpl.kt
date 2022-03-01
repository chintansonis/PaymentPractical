package com.checkoutcom.checkoutpractical.data.error


import com.checkoutcom.checkoutpractical.domain.state.ErrorHandler
import com.checkoutcom.checkoutpractical.domain.state.ResultHandler
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor() : ErrorHandler {

    override fun getError(throwable: Throwable): ResultHandler.ErrorType {
        return when (throwable) {
            is IOException -> ResultHandler.ErrorType.IOError(throwable)
            is HttpException -> ResultHandler.ErrorType.HttpError(throwable, throwable.code())
            else -> ResultHandler.ErrorType.Unknown(throwable)
        }
    }

    override fun getApiError(statusCode: Int, throwable: Throwable?): ResultHandler.ErrorType {
        return ResultHandler.ErrorType.HttpError(throwable, statusCode)
    }
}