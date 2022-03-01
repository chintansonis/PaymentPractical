package com.checkoutcom.checkoutpractical.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkoutcom.checkoutpractical.R
import com.checkoutcom.checkoutpractical.domain.models.RequestPayment
import com.checkoutcom.checkoutpractical.domain.models.ResponseCheckout
import com.checkoutcom.checkoutpractical.domain.state.ResultHandler
import com.checkoutcom.checkoutpractical.domain.usecases.PostPaymentProcessUseCase
import com.checkoutcom.checkoutpractical.utils.CardInputState
import com.checkoutcom.checkoutpractical.utils.CardUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.toLongOrDefault
import javax.inject.Inject

@HiltViewModel
class ProceedCheckOutViewModel @Inject constructor(
    private val postPaymentProcessUseCase: PostPaymentProcessUseCase,
) : ViewModel() {

    val cardInputUiState = MutableLiveData<CardInputState>()

    // livedata for observing network error messages
    private val _errorMessage = MutableLiveData<Int?>()
    val errorMessage: LiveData<Int?> = _errorMessage

    // livedata for observing response from webservice
    private val _responseCheckoutLiveData = MutableLiveData<ResultHandler<ResponseCheckout?>>()
    val responseCheckoutLiveData: LiveData<ResultHandler<ResponseCheckout?>> =
        _responseCheckoutLiveData

    // live data which directly bind to xml for updating views
    val cardNumber = MutableLiveData("")
    val cardExpiry = MutableLiveData("")
    val cardCVV = MutableLiveData("")

    //Click of Proceed To Payment
    fun onClickProceedToPayment() {
        if (isCardValid()) {
            viewModelScope.launch {
                val postPaymentProcess =
                    postPaymentProcessUseCase.execute(RequestPayment(cvv = cardCVV.value,
                        CardUtil.returnExpiryDateOfMonthYear(cardExpiry.value)?.get(0),
                        CardUtil.returnExpiryDateOfMonthYear(cardExpiry.value)?.get(1),
                        "https://fail.com",
                        cardNumber.value,
                        "https://www.success.com"))
                _responseCheckoutLiveData.value = postPaymentProcess
                _errorMessage.value = determineErrorMessage(postPaymentProcess.error)
            }
        }

    }

    private fun isCardValid(): Boolean {
        val cardNumber = cardNumber.value.toString()
        val cardExpiryDate = cardExpiry.value.toString()
        val cardCvvInput = cardCVV.value.toString()
        return when {
            !CardUtil.isValidCardNumber(cardNumber.toLongOrDefault(0)) -> {
                cardInputUiState.postValue(CardInputState.InValidCardNumberState)
                false
            }
            !CardUtil.isValidExpirationDate(cardExpiryDate) -> {
                cardInputUiState.postValue(CardInputState.InValidExpiryDateState)
                false
            }
            !CardUtil.isValidCvv(cardCvvInput, CardUtil.getCardType(cardNumber)) -> {
                cardInputUiState.postValue(CardInputState.InValidCvvState)
                false
            }
            else -> {
                cardInputUiState.postValue(CardInputState.ValidCardCredentialsState)
                true
            }
        }
    }
}

/**
 * determining error messages
 */
private fun determineErrorMessage(error: ResultHandler.ErrorType?) = when (error) {
    is ResultHandler.ErrorType.HttpError -> R.string.server_error
    is ResultHandler.ErrorType.IOError -> R.string.connection_error
    is ResultHandler.ErrorType.Unknown -> R.string.generic_error
    null -> null
}

