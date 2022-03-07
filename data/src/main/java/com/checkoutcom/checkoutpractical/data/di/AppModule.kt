package com.checkoutcom.checkoutpractical.data.di

import android.content.Context
import com.checkoutcom.checkoutpractical.data.error.ErrorHandlerImpl
import com.checkoutcom.checkoutpractical.domain.state.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Module for all globally required dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindErrorHandler(errorHandlerImpl: ErrorHandlerImpl): ErrorHandler

    companion object {
        @Singleton
        @Provides
        fun provideApplicationContext(@ApplicationContext context: Context) = context
    }

}
