package com.checkoutcom.checkoutpractical.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkoutcom.checkoutpractical.domain.usecases.GetPaymentStatusFromDataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CheckOutMainViewModel @Inject constructor(private val getPaymentStatusFromDataStoreUseCase: GetPaymentStatusFromDataStoreUseCase) :
    ViewModel() {

    private val _paymentStatus = MutableLiveData<String?>()
    val paymentStatus: LiveData<String?> = _paymentStatus

    init {
        getPaymentStatus()
    }

    /**
     * there are 3 status for payment:
     * 1. payment not initiated default state
     * 2. payment in progress: restrict user to leave the 3dsecure screen after entered the secure code
     * 3. payment completed while completing the processing from 3dsecure screen
     *
     * the purpose of storing 3 state to preserve the payment state in worst case scenario like user kill the app. Therefore, once he come
     * back to app, user has idea about the payment state by showing conclusion screen.
     * However, currently only maintain the state and restrict the user to leave the app after entering the 3dsecure screen
     */

    // to check payment status for restricing user to leave the 3dsecure screen while in payment is in processing state.
    fun getPaymentStatus(): String? {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                getPaymentStatusFromDataStoreUseCase.execute(Unit).collect {
                    _paymentStatus.value = it
                }
            }
        }
        return paymentStatus.value
    }
}