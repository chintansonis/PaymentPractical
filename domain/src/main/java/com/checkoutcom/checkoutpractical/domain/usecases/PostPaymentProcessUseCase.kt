package com.checkoutcom.checkoutpractical.domain.usecases

import com.checkoutcom.checkoutpractical.domain.models.RequestPayment
import com.checkoutcom.checkoutpractical.domain.models.ResponseCheckout
import com.checkoutcom.checkoutpractical.domain.repository.CheckoutRepository
import com.checkoutcom.checkoutpractical.domain.state.ResultHandler
import javax.inject.Inject

/**
 * PostPaymentProcessUseCase to interact between remote repository and UI Layer
 * Following Clean architecture using MVVM Design pattern
 * https://www.raywenderlich.com/3595916-clean-architecture-tutorial-for-android-getting-started
 */
class PostPaymentProcessUseCase @Inject constructor(
    private val checkoutRepository: CheckoutRepository,
) : SuspendUseCase<RequestPayment, @JvmSuppressWildcards ResultHandler<ResponseCheckout>> {

    override suspend fun execute(params: RequestPayment) =
        checkoutRepository.postCheckoutProcess(params)
}