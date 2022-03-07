package com.checkoutcom.checkoutpractical.data.di

import com.checkoutcom.checkoutpractical.data.repository.CheckoutRepositoryImpl
import com.checkoutcom.checkoutpractical.data.repository.JetPackDataStoreRepositoryImpl
import com.checkoutcom.checkoutpractical.domain.repository.CheckoutRepository
import com.checkoutcom.checkoutpractical.domain.repository.JetPackDataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCheckoutRepository(
        checkOutRepositoryImpl: CheckoutRepositoryImpl,
    ): CheckoutRepository

    @Binds
    abstract fun bindUJetPackDataStoreRepository(jetPackDataStoreRepositoryImpl: JetPackDataStoreRepositoryImpl): JetPackDataStoreRepository

}
