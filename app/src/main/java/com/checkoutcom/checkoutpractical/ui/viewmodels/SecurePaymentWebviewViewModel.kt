package com.checkoutcom.checkoutpractical.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkoutcom.checkoutpractical.domain.usecases.StorePaymentStatusToDataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurePaymentWebviewViewModel @Inject constructor(private val storePaymentStatusToDataStoreUseCase: StorePaymentStatusToDataStoreUseCase) :
    ViewModel() {

    // live data for setting up message final confirmation of payment
    private val _paymentConclusionMessage = MutableLiveData<String>()
    val paymentConclusionMessage: LiveData<String> = _paymentConclusionMessage

    // livedata for notifying user for data loading
    private val _isDataLoading = MutableLiveData<Boolean>()
    val isDataLoading: LiveData<Boolean> = _isDataLoading

    fun setPaymentConclusionMessage(paymentMessage: String) {
        _paymentConclusionMessage.value = paymentMessage
    }

    fun setisDataLoading(isDataLoading: Boolean) {
        _isDataLoading.value = isDataLoading
    }

    fun storePaymentStatus(paymentStatus: String) {
        viewModelScope.launch {
            storePaymentStatusToDataStoreUseCase.execute(paymentStatus)
        }
    }
}