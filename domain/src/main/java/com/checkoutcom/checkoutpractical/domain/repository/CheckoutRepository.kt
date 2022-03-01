package com.checkoutcom.checkoutpractical.domain.repository

import com.checkoutcom.checkoutpractical.domain.models.RequestPayment
import com.checkoutcom.checkoutpractical.domain.models.ResponseCheckout
import com.checkoutcom.checkoutpractical.domain.state.ResultHandler

/**
 * Exposing Core abstract data of remote repository  to UI layer using interactors
 */
interface CheckoutRepository {
    suspend fun postCheckoutProcess(requestPayment: RequestPayment): ResultHandler<ResponseCheckout>
}
