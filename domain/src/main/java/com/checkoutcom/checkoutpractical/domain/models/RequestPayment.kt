package com.checkoutcom.checkoutpractical.domain.models

/**
 * RequestPayment data class for passing request to webservice
 */
data class RequestPayment(
    val cvv: String?,
    val expiry_month: String?,
    val expiry_year: String?,
    val failure_url: String?,
    val number: String?,
    val success_url: String?,
)