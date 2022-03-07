package com.checkoutcom.checkoutpractical.data.jetpackdatastore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore("CheckoutPractical")
val PAYMENT_STATUS = stringPreferencesKey("payment_status")
