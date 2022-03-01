package com.checkoutcom.checkoutpractical.data.di

import com.checkoutcom.checkoutpractical.data.error.ErrorHandlerImpl
import com.checkoutcom.checkoutpractical.domain.state.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * Module for all globally required dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindErrorHandler(errorHandlerImpl: ErrorHandlerImpl): ErrorHandler

}
