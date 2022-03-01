package com.checkoutcom.checkoutpractical.data.repository

import com.checkoutcom.checkoutpractical.data.remote.CheckOutApiService
import com.checkoutcom.checkoutpractical.domain.models.RequestPayment
import com.checkoutcom.checkoutpractical.domain.repository.CheckoutRepository
import com.checkoutcom.checkoutpractical.domain.state.ErrorHandler
import com.checkoutcom.checkoutpractical.domain.state.ResultHandler
import retrofit2.Response
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(
    private val checkOutApiService: CheckOutApiService, private val errorHandler: ErrorHandler,
) : CheckoutRepository {

    override suspend fun postCheckoutProcess(requestPayment: RequestPayment) = try {
        apiResult(checkOutApiService.postPaymentRequest(requestPayment))
    } catch (e: Throwable) {
        ResultHandler.Error(errorHandler.getError(e))
    }


    private fun <T> apiResult(response: Response<T>): ResultHandler<T> {
        val data = response.body()
        ResultHandler.Loading(data)
        return if (response.isSuccessful && data != null) {
            ResultHandler.Success(data)
        } else {
            ResultHandler.Error(errorHandler.getApiError(response.code()))
        }
    }


}