package com.checkoutcom.checkoutpractical.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkoutcom.checkoutpractical.domain.usecases.StorePaymentStatusToDataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentConclusionViewModel @Inject constructor(private val storePaymentStatusToDataStoreUseCase: StorePaymentStatusToDataStoreUseCase) :
    ViewModel() {

    // update payment status in the completion stage
    fun storePaymentStatus(paymentStatus: String) {
        viewModelScope.launch {
            storePaymentStatusToDataStoreUseCase.execute(paymentStatus)
        }
    }


}