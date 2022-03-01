package com.checkoutcom.checkoutpractical.utils


sealed class CardInputState {
    object ValidCardCredentialsState : CardInputState()
    object InValidCardNumberState : CardInputState()
    object InValidExpiryDateState : CardInputState()
    object InValidCvvState : CardInputState()
}