package com.checkoutcom.checkoutpractical.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.checkoutcom.checkoutpractical.data.R
import com.checkoutcom.checkoutpractical.data.jetpackdatastore.PAYMENT_STATUS
import com.checkoutcom.checkoutpractical.data.jetpackdatastore.dataStore
import com.checkoutcom.checkoutpractical.domain.repository.JetPackDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JetPackDataStoreRepositoryImpl @Inject constructor(private val context: Context) :
    JetPackDataStoreRepository {

    override suspend fun passPaymentStatus(paymentStatus: String) {
        context.dataStore.edit { payment_status ->
            payment_status[PAYMENT_STATUS] = paymentStatus
        }
    }

    override suspend fun getPaymentStatus(): Flow<String> {
        return getPaymentStatusFlow()
    }

    private fun getPaymentStatusFlow() = context.dataStore.data
        .map { preferences ->
            preferences[PAYMENT_STATUS] ?: context.getString(R.string.payment_not_started)
        }
}