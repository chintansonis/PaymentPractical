package com.checkoutcom.checkoutpractical.domain.repository

import kotlinx.coroutines.flow.Flow


/**
 * Exposing Core abstract data of remote repository  to UI layer using interactors
 */
interface JetPackDataStoreRepository {
    suspend fun passPaymentStatus(paymentStatus: String)

    suspend fun getPaymentStatus(): Flow<String>
}
