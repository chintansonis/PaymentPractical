package com.checkoutcom.checkoutpractical.data.remote

import com.checkoutcom.checkoutpractical.domain.models.RequestPayment
import com.checkoutcom.checkoutpractical.domain.models.ResponseCheckout
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CheckOutApiService {

    @Headers("Content-Type: application/json")
    @POST("pay")
    suspend fun postPaymentRequest(@Body body: RequestPayment): Response<ResponseCheckout>

}
